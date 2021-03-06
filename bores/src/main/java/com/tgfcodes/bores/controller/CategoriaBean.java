package com.tgfcodes.bores.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;

import com.tgfcodes.bores.messages.Mensagem;
import com.tgfcodes.bores.model.Categoria;
import com.tgfcodes.bores.service.CategoriaService;

@ViewScoped
@Named("categoriaBean")
public class CategoriaBean implements Serializable{

	private static final long serialVersionUID = 1L;
	@Autowired
	private CategoriaService categoriaService;
	private Categoria categoria;
	private LazyDataModel<Categoria> lazyDataModel;
	
	@PostConstruct
	public void inicializar() {
		this.nova();
		this.lazyDataModel = this.categoriaService.pesquisar();
	}
	
	public void nova() {
		this.categoria = new Categoria();
	}
	
	public void salvar() {
		this.categoriaService.salvar(this.categoria);
		Mensagem.info("Categoria salva com sucesso.");
		this.nova();
	}

	public void excluir() {
		this.categoriaService.excluir(this.categoria);
		Mensagem.info("Categoria excluída com sucesso.");
		this.nova();
	}
	
	public List<Categoria> listar(){
		return this.categoriaService.listar();
	}
	
	public void selecionar(Categoria categoria) {
		this.categoria = categoria;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}
	
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	public LazyDataModel<Categoria> getLazyDataModel() {
		return lazyDataModel;
	}
	public void setLazyDataModel(LazyDataModel<Categoria> lazyDataModel) {
		this.lazyDataModel = lazyDataModel;
	}
	
}