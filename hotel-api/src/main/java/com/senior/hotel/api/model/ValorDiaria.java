package com.senior.hotel.api.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "VALOR_DIARIA")
public class ValorDiaria {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "valor_dia_semana", length = 20)
    private BigDecimal valorDiaSemana;

    @Column(name = "valor_fds", precision = 10, scale = 2)
    private BigDecimal valorFds;

    @Column(name = "valor_veiculo_semana", precision = 10, scale = 2)
    private BigDecimal valorVeiculoSemana;
    
    @Column(name = "valor_veiculo_fds", precision = 10, scale = 2)
    private BigDecimal valorVeiculoFds;
    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getValorDiaSemana() {
		return valorDiaSemana;
	}

	public void setValorDiaSemana(BigDecimal valorDiaSemana) {
		this.valorDiaSemana = valorDiaSemana;
	}

	public BigDecimal getValorFds() {
		return valorFds;
	}

	public void setValorFds(BigDecimal valorFds) {
		this.valorFds = valorFds;
	}

	public BigDecimal getValorVeiculoSemana() {
		return valorVeiculoSemana;
	}

	public void setValorVeiculoSemana(BigDecimal valorVeiculoSemana) {
		this.valorVeiculoSemana = valorVeiculoSemana;
	}

	public BigDecimal getValorVeiculoFds() {
		return valorVeiculoFds;
	}

	public void setValorVeiculoFds(BigDecimal valorVeiculoFds) {
		this.valorVeiculoFds = valorVeiculoFds;
	}
    

    
}
