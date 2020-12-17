package com.tgfcodes.bores.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tgfcodes.bores.model.ItemPedido;
import com.tgfcodes.bores.model.Produto;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long>{

	public boolean existsByProduto(Produto produto);
	
	
}
