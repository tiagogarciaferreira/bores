package com.tgfcodes.bores.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;

import com.tgfcodes.bores.messages.Mensagem;
import com.tgfcodes.bores.model.Cliente;
import com.tgfcodes.bores.model.enumeration.TipoPessoa;
import com.tgfcodes.bores.model.enumeration.TipoTelefone;
import com.tgfcodes.bores.service.ClienteService;
import com.tgfcodes.bores.util.FacesUtil;

@ViewScoped
@Named("clienteBean")
public class ClienteBean implements Serializable{

	private static final long serialVersionUID = 1L;
	@Autowired
	private ClienteService clienteService;
	private Cliente cliente;
	private LazyDataModel<Cliente> lazyDataModel;
	
	@PostConstruct
	public void inicializar() {
		this.verificarAcao();
	}
	
	public void novo() {
		this.cliente = new Cliente();
	}
	
	public void salvar() {
		this.clienteService.salvar(this.cliente);
		Mensagem.info("Cliente salvo com sucesso.");
		this.novo();
	}

	public void excluir() {
		this.clienteService.excluir(this.cliente);
		Mensagem.info("Cliente excluído com sucesso.");
		this.novo();
	}
	
	private void verificarAcao() {
		var clienteId = FacesUtil.getParameter("cliente");
		if (clienteId != null) {
			this.cliente = this.clienteService.buscarPorId(Long.parseLong(clienteId));
		} else {
			this.novo();
			this.lazyDataModel = this.clienteService.pesquisar();
		}
	}
	
	public List<Cliente> completarCliente(String nome){
		return this.clienteService.completar(nome);
	} 
	
	public TipoPessoa[] tiposPessoa() {
		return TipoPessoa.values();
	}

	public TipoTelefone[] tiposTelefone() {
		return TipoTelefone.values();
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public LazyDataModel<Cliente> getLazyDataModel() {
		return lazyDataModel;
	}
	public void setLazyDataModel(LazyDataModel<Cliente> lazyDataModel) {
		this.lazyDataModel = lazyDataModel;
	}
	
}