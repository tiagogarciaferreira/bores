package com.tgfcodes.bores.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tgfcodes.bores.model.ItemPedido;
import com.tgfcodes.bores.model.Pedido;
import com.tgfcodes.bores.repository.PedidoRepository;

@Service
public class EstoqueService {
	
	@Autowired
	private PedidoRepository pedidoRepository;

	public void baixarItensEstoque(Pedido pedido) {
		pedido = this.pedidoRepository.findById(pedido.getId()).get();
		for(ItemPedido item : pedido.getItens()) {
			item.getProduto().baixarEstoque(item.getQuantidade());
		}
	}
	
	public void voltarItensEstoque(Pedido pedido) {
		pedido = this.pedidoRepository.findById(pedido.getId()).get();
		for(ItemPedido item : pedido.getItens()) {
			item.getProduto().voltarEstoque(item.getQuantidade());
		}
	}

}
