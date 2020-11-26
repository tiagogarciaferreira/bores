package com.tgfcodes.bores.service;

import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tgfcodes.bores.model.Pedido;
import com.tgfcodes.bores.repository.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Transactional(readOnly = false)
	public void salvar(Pedido pedido) {
		this.pedidoRepository.save(pedido);
	}
	
	@Transactional(readOnly = true)
	public LazyDataModel<Pedido> pesquisar(){
		return this.pedidoRepository.pesquisar();
	}
	
	@Transactional(readOnly = true)
	public Pedido buscarPorId(Long id) {
		return this.pedidoRepository.findById(id).get();
	}

}
