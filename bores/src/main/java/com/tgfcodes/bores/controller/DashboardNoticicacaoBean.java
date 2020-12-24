package com.tgfcodes.bores.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;
import org.springframework.beans.factory.annotation.Autowired;

import com.tgfcodes.bores.model.Produto;
import com.tgfcodes.bores.service.EstoqueService;

@RequestScoped
@Named("dashboardNoticicacaoBean")
public class DashboardNoticicacaoBean {

	@Autowired
	private EstoqueService estoqueService;
	private List<Produto> produtos;
	private MenuModel menuModel; 
	

	@PostConstruct
	public void inicializar() {
		this.produtos = this.estoqueService.verificarEstoqueBaixo();
		this.criarNotificacao();
	}
	
	
	public void criarNotificacao() {
		DefaultMenuItem item = DefaultMenuItem.builder()
		.styleClass("no-hover")
		.style("color:red;")
		.value("A")
		.title("Teste")
		.build();
		
		this.menuModel = new DefaultMenuModel();  
		this.menuModel.getElements().add(item);
		this.menuModel.getElements().add(item);
		this.menuModel.getElements().add(item);
		this.menuModel.getElements().add(item);
		
	}
	
	public MenuModel getMenuModel() {
		return menuModel;
	}
	
	public List<Produto> getProdutos() {
		return produtos;
	}
}
