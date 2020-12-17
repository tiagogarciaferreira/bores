package com.tgfcodes.bores.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;

import com.tgfcodes.bores.messages.Mensagem;
import com.tgfcodes.bores.model.Produto;
import com.tgfcodes.bores.model.Subcategoria;
import com.tgfcodes.bores.service.ProdutoService;
import com.tgfcodes.bores.service.SubcategoriaService;
import com.tgfcodes.bores.util.FacesUtil;

@ViewScoped
@Named("produtoBean")
public class ProdutoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@Autowired
	private ProdutoService produtoService;
	@Autowired
	private SubcategoriaService subcategoriaService;
	private Produto produto;
	private LazyDataModel<Produto> lazyDataModel;
	private List<Subcategoria> subcategorias;
	private static final Integer estoqueNormal = 20;

	@PostConstruct
	public void inicializar() {
		this.verificarAcao();
	}

	public void novo() {
		this.produto = new Produto();
	}

	public void salvar() {
		this.produtoService.salvar(this.produto);
		Mensagem.info("Produto salvo com sucesso.");
		this.novo();
	}

	public void excluir() {
		this.produtoService.excluir(this.produto);
		Mensagem.info("Produto excluído com sucesso.");
		this.novo();
	}

	private void verificarAcao() {
		var produtoId = FacesUtil.getParameter("produto");
		if (produtoId != null) {
			this.produto = this.produtoService.buscarPorId(Long.parseLong(produtoId));
			this.subcategorias = subcategoriaService.listar(this.produto.getSubcategoria().getCategoria().getId());
		} else {
			this.novo();
			this.lazyDataModel = this.produtoService.pesquisar();
		}
	}

	public void buscarSubcategorias(SelectEvent<SubcategoriaBean> event) {
		var categoriaId = (event.getObject() != null) ? String.valueOf(event.getObject()) : "0";
		this.subcategorias = this.subcategoriaService.listar(Long.parseLong(categoriaId));
	}

	public List<Produto> completarProduto(String nome){
		return this.produtoService.completar(nome);
	} 
	
	public void selecionar(Produto produto) {
		this.produto = produto;
	}

	public Produto getProduto() {
		return produto;
	}

	
	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public LazyDataModel<Produto> getLazyDataModel() {
		return lazyDataModel;
	}

	public void setLazyDataModel(LazyDataModel<Produto> lazyDataModel) {
		this.lazyDataModel = lazyDataModel;
	}

	public Integer getEstoqueNormal() {
		return estoqueNormal;
	}
	
	public List<Subcategoria> getSubcategorias() {
		return subcategorias;
	}

}