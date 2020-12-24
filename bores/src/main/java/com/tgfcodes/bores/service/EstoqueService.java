package com.tgfcodes.bores.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tgfcodes.bores.dto.EstatisticasEstoqueDTO;
import com.tgfcodes.bores.model.ItemPedido;
import com.tgfcodes.bores.model.Pedido;
import com.tgfcodes.bores.model.Produto;
import com.tgfcodes.bores.repository.PedidoRepository;
import com.tgfcodes.bores.repository.ProdutoRepository;

@Service
@PropertySource("classpath:notificacao.properties")
public class EstoqueService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;

	@Transactional(readOnly = false)
	public EstatisticasEstoqueDTO totalEstoque() {
		return this.produtoRepository.totalEstoque();
	}
	
	@Transactional(readOnly = false)
	public void baixarItensEstoque(Pedido pedido) {
		pedido = this.pedidoRepository.findById(pedido.getId()).get();
		for(ItemPedido item : pedido.getItens()) {
			item.getProduto().baixarEstoque(item.getQuantidade());
		}
	}
	
	@Transactional(readOnly = false)
	public void voltarItensEstoque(Pedido pedido) {
		pedido = this.pedidoRepository.findById(pedido.getId()).get();
		for(ItemPedido item : pedido.getItens()) {
			item.getProduto().voltarEstoque(item.getQuantidade());
		}
	}
	
	@Transactional(readOnly = true)
	public List<Produto> verificarEstoqueBaixo(){
		return this.produtoRepository.findByQuantidadeEstoqueLessThan(5000);
	}

}
