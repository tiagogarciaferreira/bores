package com.tgfcodes.bores.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tgfcodes.bores.model.Cliente;
import com.tgfcodes.bores.repository.helper.cliente.ClienteQueries;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>, ClienteQueries {

	
}
