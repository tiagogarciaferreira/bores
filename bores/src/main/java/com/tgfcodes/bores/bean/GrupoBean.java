package com.tgfcodes.bores.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import com.tgfcodes.bores.model.Grupo;
import com.tgfcodes.bores.service.GrupoService;

@ViewScoped
@Named("grupoBean")
public class GrupoBean implements Serializable{

	private static final long serialVersionUID = 1L;
	@Autowired
	private GrupoService grupoService;
	
	@PostConstruct
	public void inicializar() {}
	
	public List<Grupo> listar(){
		return this.grupoService.listar();
	}
	
}