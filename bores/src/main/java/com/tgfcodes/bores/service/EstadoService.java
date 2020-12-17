package com.tgfcodes.bores.service;

import java.util.List;

import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tgfcodes.bores.exception.NegocioException;
import com.tgfcodes.bores.model.Estado;
import com.tgfcodes.bores.repository.CidadeRepository;
import com.tgfcodes.bores.repository.EstadoRepository;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Transactional(readOnly = false)
	public void salvar(Estado estado) {
		this.estadoRepository.save(estado);
	}
	
	@Transactional(readOnly = false)
	public void excluir(Estado estado) {
		boolean remover = this.cidadeRepository.existsByEstado(estado);
		if(remover) { 
			throw new NegocioException("Estado não pode ser excluído. Possue cidades associadas."); 
		}
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
	public Estado buscarPorId(Long estadoId) {
		return this.estadoRepository.findById(estadoId).get();
	}
	
}
