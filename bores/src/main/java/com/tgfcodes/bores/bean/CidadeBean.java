package com.tgfcodes.bores.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;

import com.tgfcodes.bores.messages.Mensagem;
import com.tgfcodes.bores.model.Cidade;
import com.tgfcodes.bores.service.CidadeService;

@ViewScoped
@Named("cidadeBean")
public class CidadeBean implements Serializable{

	private static final long serialVersionUID = 1L;
	@Autowired
	private CidadeService cidadeService;
	private Cidade cidade;
	private LazyDataModel<Cidade> lazyDataModel;
	
	@PostConstruct
	public void inicializar() {
		this.nova();
		this.lazyDataModel = this.cidadeService.pesquisar();
	}
	
	public void nova() {
		this.cidade = new Cidade();
	}
	
	public void salvar() {
		this.cidadeService.salvar(this.cidade);
		Mensagem.info("Cidade: ", "Salva com sucesso.");
		this.nova();
	}

	public void excluir() {
		this.cidadeService.excluir(this.cidade);
		Mensagem.info("Cidade: ", "Excluída com sucesso.");
		this.nova();
	}
	
	public void selecionar(Cidade cidade) {
		this.cidade = cidade;
	}
	
	public Cidade getCidade() {
		return cidade;
	}
	
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	
	public LazyDataModel<Cidade> getLazyDataModel() {
		return lazyDataModel;
	}
	public void setLazyDataModel(LazyDataModel<Cidade> lazyDataModel) {
		this.lazyDataModel = lazyDataModel;
	}
	
}