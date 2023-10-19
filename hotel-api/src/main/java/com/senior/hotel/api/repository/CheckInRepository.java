package com.senior.hotel.api.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.senior.hotel.api.model.CheckIn;

public interface CheckInRepository extends JpaRepository<CheckIn, Long>{
	
	@Query("SELECT c FROM CheckIn c WHERE c.hospede.documento = :documento ORDER BY 1 DESC LIMIT 1")
    CheckIn buscaSituacaoUltimaHospedagem(@Param("documento") Long documento);
	
	@Query("SELECT sum(valorEstadia) FROM CheckIn c WHERE c.hospede.documento = :documento")
	BigDecimal valorTotalGasto(@Param("documento") Long documento);

}
