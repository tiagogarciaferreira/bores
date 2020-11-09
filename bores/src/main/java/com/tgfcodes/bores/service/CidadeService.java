package com.tgfcodes.bores.service;

import java.util.List;

import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tgfcodes.bores.model.Cidade;
import com.tgfcodes.bores.model.Estado;
import com.tgfcodes.bores.repository.CidadeRepository;
import com.tgfcodes.bores.repository.EstadoRepository;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Transactional(readOnly = false)
	public void salvar(Cidade cidade) {
		this.cidadeRepository.save(cidade);
	}
	
	@Transactional(readOnly = false)
	public void excluir(Cidade cidade) {
		cidade = this.buscarPorId(cidade.getId());
		this.cidadeRepository.delete(cidade);
	}
	
	@Transactional(readOnly = true)
	public LazyDataModel<Cidade> pesquisar(){
		return this.cidadeRepository.pesquisar();
	}
	
	@Transactional(readOnly = true)
	public List<Cidade> listar(){
		return this.cidadeRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<Cidade> listar(Long estadoId){
		Estado estado = this.estadoRepository.findById(estadoId).get();
		return this.cidadeRepository.findByEstado(estado);
	}
	
	@Transactional(readOnly = true)
	public Cidade buscarPorId(Long id) {
		return this.cidadeRepository.findById(id).get();
	}
	
}
