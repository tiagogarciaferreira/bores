package com.tgfcodes.bores.service;

import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tgfcodes.bores.model.Cliente;
import com.tgfcodes.bores.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Transactional(readOnly = false)
	public void salvar(Cliente cliente) {
		this.clienteRepository.save(cliente);
	}
	
	@Transactional(readOnly = false)
	public void excluir(Cliente cliente) {
		cliente = this.buscarPorId(cliente.getId());
		//TODO: não pode excluir se pertencer a algum pedido
		this.clienteRepository.delete(cliente);
	}
	
	@Transactional(readOnly = true)
	public LazyDataModel<Cliente> pesquisar(){
		return this.clienteRepository.pesquisar();
	}
	
	@Transactional(readOnly = true)
	public Cliente buscarPorId(Long id) {
		return this.clienteRepository.findById(id).get();
	}
	
}
