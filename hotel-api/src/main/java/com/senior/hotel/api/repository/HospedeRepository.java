package com.senior.hotel.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.senior.hotel.api.model.Hospede;

public interface HospedeRepository extends JpaRepository<Hospede, Long>{
	
	@Query("SELECT h FROM Hospede h WHERE h.documento = :documento")
    Hospede findByDocumento(@Param("documento") Long documento);
	
	@Query("SELECT h FROM Hospede h WHERE h.nome LIKE %:nome% ")
	Hospede buscarHospedesPeloNome(@Param("nome") String nome);
	
	@Query("SELECT h FROM Hospede h WHERE h.documento = :documento ")
	Hospede buscarHospedesPeloDocumento(@Param("documento") Long documento);
	
	@Query("SELECT h FROM Hospede h WHERE h.telefone = :telefone ")
	Hospede buscarHospedesPeloTelefone(@Param("telefone") Long telefone);
}
