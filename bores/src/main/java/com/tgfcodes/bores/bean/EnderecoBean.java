package com.tgfcodes.bores.bean;

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
	private List<Cidade> cidades;
	private List<Endereco> enderecos;

	@PostConstruct
	public void inicializar() {
		this.novo();
	}

	private void novo() {
		Long clienteAtual = null;
		if(this.endereco != null && this.endereco.getCliente().getId() != null) {
			clienteAtual = this.endereco.getCliente().getId();
		}
		this.endereco = new Endereco();
		this.endereco.getCliente().setId(clienteAtual);
	}

	public void salvar() {
		this.enderecoService.salvar(this.endereco);
		Mensagem.info("Endereço: ", "Salvo com sucesso.");
		this.novo();
	}

	public void excluir() {
		this.enderecoService.excluir(this.endereco);
		this.enderecos.remove(this.endereco);
		Mensagem.info("Endereço: ", "Excluído com sucesso.");
		this.novo();
	}
	
	public void selecionarCliente(TabChangeEvent<EnderecoBean> event) {
		var tabAtiva = event.getTab().getId();
		if(tabAtiva.equals("endereco")) {
			var clienteId = (Long) event.getComponent().getAttributes().get("cliente");
			this.endereco.getCliente().setId(clienteId);
			this.enderecos = this.enderecoService.listar(clienteId);
		}
	}
	
	public void selecionarEndereco() {
		this.cidades = this.cidadeService.listar(this.endereco.getEstado().getId());
	}
	
	public void buscarCidades(SelectEvent<EnderecoBean> event) {
		var estadoId = (event.getObject() != null) ? String.valueOf(event.getObject()) : "0";
		this.endereco.setEstado(this.estadoService.buscarPorId(Long.parseLong(estadoId)));
		this.cidades = this.cidadeService.listar(Long.parseLong(estadoId));
	}
	
	public List<Cidade> getCidades() {
		return cidades;
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
	
	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}
	
}