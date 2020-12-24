package com.tgfcodes.bores.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import com.tgfcodes.bores.model.Usuario;
import com.tgfcodes.bores.security.Security;

@ViewScoped
@Named("perfilBean")
public class PerfilBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private Security security;
	private Usuario usuario;

	@PostConstruct
	public void inicializar() {
		this.usuario = this.security.getUsuarioLogado().getUsuario();
	}

	public Usuario getUsuario() {
		return usuario;
	}
}
