package com.senior.hotel.api.resource;

import java.math.BigDecimal;
import java.net.URI;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.senior.hotel.api.model.CheckIn;
import com.senior.hotel.api.model.Hospede;
import com.senior.hotel.api.model.ValorDiaria;
import com.senior.hotel.api.repository.CheckInRepository;
import com.senior.hotel.api.repository.HospedeRepository;
import com.senior.hotel.api.repository.ValorDiariaRepository;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/hospedes")
public class HospedeResource {
	
	@Autowired
	private HospedeRepository hospedeRepository;
	
	@Autowired
	private CheckInRepository checkInRepository;
	
	@Autowired
	private ValorDiariaRepository valorDiariaRepository;
	
	private static final int VALOR_PADRAO_DIARIA = 1;
	
	
	@GetMapping
	public List<Hospede> listarHospedes(){
		return hospedeRepository.findAll();
	}
	
	@PostMapping("/cadastrar-hospede")
	public ResponseEntity<?> cadastrarCliente(@RequestBody Hospede hospede, HttpServletResponse response) {
		
	    Hospede hospedeExistente = hospedeRepository.findByDocumento(hospede.getDocumento());

	    if (hospedeExistente != null) {
	        return ResponseEntity.badRequest().body("Cliente já cadastrado");
	    }

	    Hospede hospedeCadastrado = hospedeRepository.save(hospede);

	    URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{documento}")
	            .buildAndExpand(hospedeCadastrado.getDocumento()).toUri();
	    response.setHeader("Location", uri.toASCIIString());

	    return ResponseEntity.created(uri).body(hospedeCadastrado);
	}
	
	@GetMapping("/{documento}")
	public ResponseEntity<Hospede> buscarPeloDocumento(@PathVariable Long documento) {
		
	    Optional<Hospede> hospede = hospedeRepository.findById(documento);
	    if (hospede.isPresent()) {
	        return ResponseEntity.ok(hospede.get());
	    }

	    return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/checkin")
	public ResponseEntity<CheckInResponse> checkInCliente(@RequestBody CheckIn checkIn,HttpServletResponse response) {				
		
		ValorDiaria  valorDiaria = valorDiariaRepository.findByTabelaValores(VALOR_PADRAO_DIARIA);
		Hospede hospedeExistente = hospedeRepository.findByDocumento(checkIn.getHospede().getDocumento());
		CheckIn ultimaHospedagem = checkInRepository.buscaSituacaoUltimaHospedagem(checkIn.getHospede().getDocumento());
		BigDecimal valorTotalGasto = checkInRepository.valorTotalGasto(checkIn.getHospede().getDocumento());
	    
	    if (hospedeExistente == null) {
	    	return ResponseEntity.badRequest()
	    			.body(new CheckInResponse("Cliente não cadastrado. Por favor realizar o cadastro do cliente."));
	    }
	    
	    if (ultimaHospedagem != null ) {
	    	LocalDateTime momentoAtual = LocalDateTime.now();
	    	LocalDateTime dataSaida = ultimaHospedagem.getDataSaida();
	    	
	    	if(dataSaida.isAfter(momentoAtual)) {
	    		return ResponseEntity.badRequest().body(new CheckInResponse("Cliente ainda hospedado.", ultimaHospedagem, valorTotalGasto));
	    	}	    		    	
   	 	}
	    
	    CheckIn novoCheckIn = new CheckIn();
	    novoCheckIn.setHospede(hospedeExistente);
	    novoCheckIn.setDataEntrada(checkIn.getDataEntrada());
	    novoCheckIn.setDataSaida(checkIn.getDataSaida());
	    novoCheckIn.setAdicionalVeiculo(checkIn.isAdicionalVeiculo());
	    
	    calcularValorEstadia(novoCheckIn, valorDiaria);

	    CheckIn checkInRealizado = checkInRepository.save(novoCheckIn);
	    

	    URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/checkin/{hospede-id}")
	            .buildAndExpand(checkInRealizado.getHospede()).toUri();
	    response.setHeader("Location", uri.toASCIIString());

	    return ResponseEntity.created(uri)
	    		.body(new CheckInResponse("Check in realizado com Sucesso", checkInRealizado, valorTotalGasto));
	}
	
	
	
	@GetMapping("/checkin/{hospede-id}")
	public ResponseEntity<CheckIn> buscarPeloHospedeId(@PathVariable Long hospedeId) {
		
	    Optional<CheckIn> checkIn = checkInRepository.findById(hospedeId);
	    if (checkIn.isPresent()) {
	        return ResponseEntity.ok(checkIn.get());
	    }

	    return ResponseEntity.notFound().build();
	}	
	
	
	
	@GetMapping("/buscar-hospede-nome/{nome}")
	public ResponseEntity<?> buscarHospedesPorNome(@PathVariable String nome) {
		
		Hospede hospede = hospedeRepository.buscarHospedesPeloNome(nome);
		if (hospede != null) {
	        return ResponseEntity.ok(hospede);
	    }

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hóspede não encontrado.");
	}
	
	@GetMapping("/buscar-hospede-documento/{documento}")
	public ResponseEntity<?> buscarHospedesPorDocoumento(@PathVariable Long documento) {
		
		Hospede hospede = hospedeRepository.buscarHospedesPeloDocumento(documento);
	    if (hospede != null) {
	        return ResponseEntity.ok(hospede);
	    }

	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hóspede não encontrado.");
	}
	
	@GetMapping("/buscar-hospede-telefone/{telefone}")
	public ResponseEntity<?> buscarHospedesPorTelefone(@PathVariable Long telefone) {
		
		Hospede hospede = hospedeRepository.buscarHospedesPeloTelefone(telefone);
	    if (hospede != null) {
	        return ResponseEntity.ok(hospede);
	    }

	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hóspede não encontrado.");
	}
	
	
	
	@GetMapping("/buscar-situacao-hospede/{hospedeId}")
	public ResponseEntity<?> buscarSitucaoHospede(@PathVariable Long hospedeId) {
		
	    CheckIn checkIn = checkInRepository.buscaSituacaoUltimaHospedagem(hospedeId);
	    BigDecimal valorTotalGasto = checkInRepository.valorTotalGasto(hospedeId);
	    
	    if (checkIn != null) {
	        LocalDateTime dataSaida = checkIn.getDataSaida();
	        LocalDateTime dataAtual = LocalDateTime.now();

	        if (dataSaida.isBefore(dataAtual)) {
	            return ResponseEntity.ok().body(new CheckInResponse("Cliente já realizou check out.", checkIn,valorTotalGasto));
	        } else {
	        	return ResponseEntity.ok().body(new CheckInResponse("Cliente ainda está hospedado.", checkIn,valorTotalGasto));
	        }
	    }

	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hóspede não encontrado.");
	}
	
	public BigDecimal calcularValorEstadia(CheckIn checkIn, ValorDiaria valorDiaria) {
		
        LocalDateTime dataEntrada = checkIn.getDataEntrada();
        LocalDateTime dataSaida = checkIn.getDataSaida();

        
        BigDecimal valorGasto = calcularValor(checkIn, valorDiaria,dataEntrada, dataSaida);
        
        checkIn.setValorEstadia(valorGasto);

        

        return valorGasto;
    }
	
	
	private BigDecimal calcularValor(CheckIn checkIn, ValorDiaria valorDiaria, LocalDateTime dataEntrada, LocalDateTime dataSaida) {
        
		LocalDate dateEntrada = dataEntrada.toLocalDate();
        LocalDate dateSaida = dataSaida.toLocalDate();
        BigDecimal valorEstadiaSemana = valorDiaria.getValorDiaSemana();
        BigDecimal valorEstadiaFDs = valorDiaria.getValorFds();
        BigDecimal valorVeiculoSemana = valorDiaria.getValorVeiculoSemana();
        BigDecimal valorVeiculoFDS = valorDiaria.getValorVeiculoFds();
        
        int diaSemana = 0;
        int diaFDS =0;
        while (!dateEntrada.isEqual(dateSaida)) {
            dateEntrada = dateEntrada.plusDays(1);
            if (dateEntrada.getDayOfWeek() != DayOfWeek.SUNDAY &&
                dateEntrada.getDayOfWeek() != DayOfWeek.SATURDAY) {
            	diaSemana++;
            }else {
            	diaFDS++;
            }
        }
        BigDecimal valorHospedagem = valorEstadiaSemana.multiply(BigDecimal.valueOf(diaSemana)).add(valorEstadiaFDs.multiply(BigDecimal.valueOf(diaFDS)));
        if(checkIn.isAdicionalVeiculo()) {
        	BigDecimal valorVeiculo = valorVeiculoSemana.multiply(BigDecimal.valueOf(diaSemana)).add(valorVeiculoFDS.multiply(BigDecimal.valueOf(diaFDS)));
        	return valorHospedagem.add(valorVeiculo);
        }
        
        return valorHospedagem;
    }

}
