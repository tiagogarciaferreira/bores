package com.tgfcodes.bores.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.component.UIOutput;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;

import com.tgfcodes.bores.messages.Mensagem;
import com.tgfcodes.bores.model.Usuario;
import com.tgfcodes.bores.service.UsuarioService;
import com.tgfcodes.bores.util.FacesUtil;

@ViewScoped
@Named("usuarioBean")
public class UsuarioBean implements Serializable{

	private static final long serialVersionUID = 1L;
	@Autowired
	private UsuarioService usuarioService;
	private Usuario usuario;
	private LazyDataModel<Usuario> lazyDataModel;
	
	@PostConstruct
	public void inicializar() {
		this.verificarAcao();
	}
	
	public void novo() {
		this.usuario = new Usuario();
	}
	
	public void salvar() {
		this.usuarioService.salvar(this.usuario);
		Mensagem.info("Usuário salvo com sucesso.");
		this.novo();
	}

	public void excluir() {
		this.usuarioService.excluir(this.usuario);
		Mensagem.info("Usuário excluído com sucesso.");
		this.novo();
	}
	
	public void updateStatus(AjaxBehaviorEvent event) {
		var usuarioId = (Long) event.getComponent().getAttributes().get("usuario");
		Boolean novoStatus = (Boolean) ((UIOutput) event.getSource()).getValue();
		this.usuarioService.updateStatus(usuarioId, novoStatus);
		Mensagem.info("Status atualizado com sucesso.");
	}
	
	public List<Usuario> buscarVendedores() {
		return this.usuarioService.buscarPorGrupo(true, "Vendedor");
	}
	 
	private void verificarAcao() {
		var usuarioId = FacesUtil.getParameter("usuario");
		if (usuarioId != null) {
			this.usuario = this.usuarioService.buscarPorId(Long.parseLong(usuarioId));
		} else {
			this.novo();
			this.lazyDataModel = this.usuarioService.pesquisar();
		}
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void selecionar(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public LazyDataModel<Usuario> getLazyDataModel() {
		return lazyDataModel;
	}
	public void setLazyDataModel(LazyDataModel<Usuario> lazyDataModel) {
		this.lazyDataModel = lazyDataModel;
	}
	
}