package com.tgfcodes.bores.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tgfcodes.bores.model.Cidade;
import com.tgfcodes.bores.model.Cliente;
import com.tgfcodes.bores.model.Endereco;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long>{

	List<Endereco> findByCliente(Cliente cliente);
	List<Endereco> findByCidade(Cidade cidade);
}
