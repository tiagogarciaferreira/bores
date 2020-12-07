package com.tgfcodes.bores.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tgfcodes.bores.model.Cliente;
import com.tgfcodes.bores.model.Pedido;
import com.tgfcodes.bores.model.Usuario;
import com.tgfcodes.bores.repository.helper.pedido.PedidoQueries;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>, PedidoQueries {

	public boolean existsByCliente(Cliente cliente);
	public boolean existsByUsuario(Usuario usuario);
}
