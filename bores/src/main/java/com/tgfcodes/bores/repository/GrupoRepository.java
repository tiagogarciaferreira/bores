package com.tgfcodes.bores.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tgfcodes.bores.model.Grupo;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long>{

	public Grupo findByNomeIgnoreCase(String nome);
	
}
