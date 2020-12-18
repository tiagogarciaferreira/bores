package com.tgfcodes.bores.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.springframework.beans.factory.annotation.Autowired;

import com.tgfcodes.bores.messages.Mensagem;
import com.tgfcodes.bores.model.Cidade;
import com.tgfcodes.bores.model.Endereco;
import com.tgfcodes.bores.service.CidadeService;
import com.tgfcodes.bores.service.EnderecoService;
import com.tgfcodes.bores.service.EstadoService;

@ViewScoped
@Named("enderecoBean")
public class EnderecoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@Autowired
	private EnderecoService enderecoService;
	@Autowired
	private CidadeService cidadeService;
	@Autowired
	private EstadoService estadoService;
	private Endereco endereco;
	private List<Endereco> enderecos;
	private String tabAtiva;

	@PostConstruct
	public void inicializar() {
		this.novo();
	}

	public void novo() {
		Long clienteAtual = null;
		if(this.endereco != null && this.endereco.getCliente().getId() != null) {
			clienteAtual = this.endereco.getCliente().getId();
		}
		this.endereco = new Endereco();
		this.endereco.getCliente().setId(clienteAtual);
		
	}

	public void salvar() {
		this.enderecoService.salvar(this.endereco);
		Mensagem.info("Endereço salvo com sucesso.");
		this.enderecos.remove(this.endereco);
		this.enderecos.add(this.endereco);
		this.novo();
	}

	public void excluir() {
		this.enderecoService.excluir(this.endereco);
		this.enderecos.remove(this.endereco);
		Mensagem.info("Endereço excluído com sucesso.");
		this.novo();
	}
	
	public void selecionarCliente(TabChangeEvent<EnderecoBean> event) {
		tabAtiva = event.getTab().getId();
		if(tabAtiva.equals("endereco")) {
			var clienteId = (Long) event.getComponent().getAttributes().get("cliente");
			this.endereco.getCliente().setId(clienteId);
			this.enderecos = this.enderecoService.listar(clienteId);
		}
	}
	
	public void buscarCidades(SelectEvent<EnderecoBean> event) {
		var estadoId = (event.getObject() != null) ? String.valueOf(event.getObject()) : "0";
		this.endereco.setEstado(this.estadoService.buscarPorId(Long.parseLong(estadoId)));
	}
	
	public List<Cidade> listarCidades(){
		if(this.endereco.getEstado().getId() != null){
			return this.cidadeService.listar(this.endereco.getEstado().getId());
		}
		return null;
	}
	
	public void selecionar(Endereco endereco) {
		this.endereco = endereco;
	}
	
	public String getTabAtiva() {
		return tabAtiva;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}
	
}