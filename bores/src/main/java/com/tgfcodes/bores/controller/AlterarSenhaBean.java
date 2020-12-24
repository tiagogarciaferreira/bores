package com.tgfcodes.bores.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import com.tgfcodes.bores.messages.Mensagem;
import com.tgfcodes.bores.model.Usuario;
import com.tgfcodes.bores.security.Security;
import com.tgfcodes.bores.service.UsuarioService;

@ViewScoped
@Named("alterarSenhaBean")
public class AlterarSenhaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private Security security;
	private Usuario usuario;

	@PostConstruct
	public void inicializar() {
		this.novo();
		this.usuario.setNome(security.getUsuarioLogado().getUsuario().getNome());
		this.usuario.setEmail(security.getUsuarioLogado().getUsername());
	}

	private void novo() {
		this.usuario = new Usuario();
	}

	public void alterar() {
		this.usuarioService.alterarSenha(this.usuario);
		Mensagem.info("Senha atualizada com sucesso.");
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
