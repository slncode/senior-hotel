package com.senior.hotel.api.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "CHECK_IN")
public class CheckIn {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "HOSPEDE_DOCUMENTO", referencedColumnName = "documento")
    private Hospede hospede;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_ENTRADA")
    private LocalDateTime dataEntrada;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_SAIDA")
    private LocalDateTime dataSaida;

    @Column(name = "ADICIONAL_VEICULO")
    private boolean adicionalVeiculo;
    
    @Column(name = "valor_estadia", precision = 10, scale = 2)
    private BigDecimal valorEstadia;
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Hospede getHospede() {
        return hospede;
    }

    public void setHospede(Hospede hospede) {
        this.hospede = hospede;
    }

    public LocalDateTime getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDateTime dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public LocalDateTime getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(LocalDateTime dataSaida) {
        this.dataSaida = dataSaida;
    }

    public boolean isAdicionalVeiculo() {
        return adicionalVeiculo;
    }

    public void setAdicionalVeiculo(boolean adicionalVeiculo) {
        this.adicionalVeiculo = adicionalVeiculo;
    }

	public BigDecimal getValorEstadia() {
		return valorEstadia;
	}

	public void setValorEstadia(BigDecimal valorEstadia) {
		this.valorEstadia = valorEstadia;
	}
    
    
}
