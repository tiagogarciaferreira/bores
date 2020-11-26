package com.tgfcodes.bores.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tgfcodes.bores.model.Permissao;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Long>{

	
}
