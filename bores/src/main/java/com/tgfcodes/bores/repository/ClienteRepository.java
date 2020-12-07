package com.tgfcodes.bores.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tgfcodes.bores.model.Cliente;
import com.tgfcodes.bores.repository.helper.cliente.ClienteQueries;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>, ClienteQueries {

	public Cliente findByEmailIgnoreCase(String email);
	@Query(value = "from Cliente c where lower(c.email) = lower(?1) or c.cpfOuCnpj = ?2")
	public Cliente porEmailOuCpfCnpj(String email, String cpfCnpj);
	@Query("from Cliente c where lower(c.nome) like lower(concat(?1, '%'))")
	public List<Cliente> porNome(String nome);
	
}
