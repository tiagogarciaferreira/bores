package com.tgfcodes.bores.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tgfcodes.bores.model.Cidade;
import com.tgfcodes.bores.model.Estado;
import com.tgfcodes.bores.repository.helper.cidade.CidadeQueries;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long>, CidadeQueries {

	List<Cidade> findByEstado(Estado estado);
}
