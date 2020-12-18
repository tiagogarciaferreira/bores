package com.tgfcodes.bores.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;

import com.tgfcodes.bores.messages.Mensagem;
import com.tgfcodes.bores.model.Estado;
import com.tgfcodes.bores.service.EstadoService;

@ViewScoped
@Named("estadoBean")
public class EstadoBean implements Serializable{

	private static final long serialVersionUID = 1L;
	@Autowired
	private EstadoService estadoService;
	private Estado estado;
	private LazyDataModel<Estado> lazyDataModel;
	
	@PostConstruct
	public void inicializar() {
		this.novo();
		this.lazyDataModel = this.estadoService.pesquisar();
	}
	
	public void novo() {
		this.estado = new Estado();
	}
	
	public void salvar() {
		this.estadoService.salvar(this.estado);
		Mensagem.info("Estado salvo com sucesso.");
		this.novo();
	}

	public void excluir() {
		this.estadoService.excluir(this.estado);
		Mensagem.info("Estado excluído com sucesso.");
		this.novo();
	}
	
	public void selecionar(Estado estado) {
		this.estado = estado;
	}
	
	public List<Estado> listar(){
		return this.estadoService.listar();
	}
	
	public Estado getEstado() {
		return estado;
	}
	
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	public LazyDataModel<Estado> getLazyDataModel() {
		return lazyDataModel;
	}
	public void setLazyDataModel(LazyDataModel<Estado> lazyDataModel) {
		this.lazyDataModel = lazyDataModel;
	}
	
}