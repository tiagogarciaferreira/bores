package com.tgfcodes.bores.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;

import com.tgfcodes.bores.messages.Mensagem;
import com.tgfcodes.bores.model.Cidade;
import com.tgfcodes.bores.model.Cliente;
import com.tgfcodes.bores.model.Endereco;
import com.tgfcodes.bores.service.CidadeService;
import com.tgfcodes.bores.service.ClienteService;
import com.tgfcodes.bores.service.EnderecoService;
import com.tgfcodes.bores.util.FacesUtil;

@ViewScoped
@Named("enderecoBean")
public class EnderecoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@Autowired
	private EnderecoService enderecoService;
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private CidadeService cidadeService;
	private Endereco endereco;
	private List<Cidade> cidades;
	private List<Endereco> enderecos;

	@PostConstruct
	public void inicializar() {
		this.verificarAcao();
		this.novo();
	}

	public void novo() {
		this.endereco = new Endereco();
	}

	public void salvar() {
		this.enderecoService.salvar(endereco);
		Mensagem.info("Endereço: ", "Salvo com sucesso.");
		this.novo();
	}

	public void excluir() {
		this.enderecoService.excluir(endereco);
		Mensagem.info("Endereço: ", "Excluído com sucesso.");
		this.novo();
	}
	
	private void verificarAcao() {
		var clienteId = FacesUtil.getParameter("cliente");
		if (clienteId != null) {
			this.novo();
			Cliente cliente = this.clienteService.buscarPorId(Long.parseLong(clienteId));
			this.endereco.setCliente(cliente);
			this.enderecos = this.enderecoService.listar(Long.parseLong(clienteId));
		} else {
			this.novo();
		}
	}
	
	public void buscarCidades(SelectEvent<EnderecoBean> event) {
		var estadoId = (event.getObject() != null) ? String.valueOf(event.getObject()) : "0";
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