package com.tgfcodes.bores.service;

import java.util.List;

import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tgfcodes.bores.model.Estado;
import com.tgfcodes.bores.repository.EstadoRepository;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository estadoRepository;
	
	@Transactional(readOnly = false)
	public void salvar(Estado estado) {
		this.estadoRepository.save(estado);
	}
	
	@Transactional(readOnly = false)
	public void excluir(Estado estado) {
		estado = this.buscarPorId(estado.getId());
		this.estadoRepository.delete(estado);
	}
	
	@Transactional(readOnly = true)
	public LazyDataModel<Estado> pesquisar(){
		return this.estadoRepository.pesquisar();
	}
	
	@Transactional(readOnly = true)
	public List<Estado> listar(){
		return this.estadoRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Estado buscarPorId(Long id) {
		return this.estadoRepository.findById(id).get();
	}
	
}
