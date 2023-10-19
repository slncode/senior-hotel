package com.senior.hotel.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.senior.hotel.api.model.ValorDiaria;

public interface ValorDiariaRepository extends JpaRepository<ValorDiaria, Long>{
	
	@Query("SELECT v FROM ValorDiaria v WHERE v.id = :id")
    ValorDiaria findByTabelaValores(@Param("id") int id);

}
