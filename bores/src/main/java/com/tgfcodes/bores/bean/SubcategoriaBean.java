package com.tgfcodes.bores.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/*import org.primefaces.event.SelectEvent;*/
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;

import com.tgfcodes.bores.messages.Mensagem;
import com.tgfcodes.bores.model.Subcategoria;
import com.tgfcodes.bores.service.SubcategoriaService;

@ViewScoped
@Named("subcategoriaBean")
public class SubcategoriaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@Autowired
	private SubcategoriaService subcategoriaService;
	private Subcategoria subcategoria;
	private LazyDataModel<Subcategoria> lazyDataModel;

	@PostConstruct
	public void inicializar() {
		this.nova();
		this.lazyDataModel = this.subcategoriaService.pesquisar();
	}
	
	public void nova() {
		this.subcategoria = new Subcategoria();
	}

	public void salvar() {
		this.subcategoriaService.salvar(subcategoria);
		Mensagem.info("Subcategoria: ", "Salva com sucesso.");
		this.nova();
	}

	public void excluir() {
		this.subcategoriaService.excluir(subcategoria);
		Mensagem.info("Subcategoria: ", "Excluída com sucesso.");
		this.nova();
	}
	
	public Subcategoria getSubcategoria() {
		return subcategoria;
	}

	public void setSubcategoria(Subcategoria subcategoria) {
		this.subcategoria = subcategoria;
	}

	public LazyDataModel<Subcategoria> getLazyDataModel() {
		return lazyDataModel;
	}

	public void setLazyDataModel(LazyDataModel<Subcategoria> lazyDataModel) {
		this.lazyDataModel = lazyDataModel;
	}

}