package com.tgfcodes.bores.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;

import com.tgfcodes.bores.messages.Mensagem;
import com.tgfcodes.bores.model.Cidade;
import com.tgfcodes.bores.model.Pedido;
import com.tgfcodes.bores.model.Produto;
import com.tgfcodes.bores.model.enumeration.FormaPagamento;
import com.tgfcodes.bores.service.CidadeService;
import com.tgfcodes.bores.service.EstadoService;
import com.tgfcodes.bores.service.PedidoService;
import com.tgfcodes.bores.util.FacesUtil;

@ViewScoped
@Named("pedidoBean")
public class PedidoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@Autowired
	private PedidoService pedidoService;
	@Autowired
	private EstadoService estadoService;
	@Autowired
	private CidadeService cidadeService;
	private Pedido pedido;
	private LazyDataModel<Pedido> lazyDataModel;
	
	private List<Produto> listaInit = new ArrayList<Produto>();

	@PostConstruct
	public void inicializar() {
		Produto p = new Produto();
		listaInit.add(p);
		this.verificarAcao();
	}
	
	public List<Produto> getListaInit() {
		return listaInit;
	}

	public void novo() {
		this.pedido = new Pedido();
	}

	public void salvar() {
		this.pedidoService.salvar(this.pedido);
		Mensagem.info("Salvo: ", "Salvo com sucesso.");
		this.novo();
	}

	private void verificarAcao() {
		var pedidoId = FacesUtil.getParameter("pedido");
		if (pedidoId != null) {
			this.pedido = this.pedidoService.buscarPorId(Long.parseLong(pedidoId));
		} else {
			this.novo();
			//TODO: desenvolver a pesquisa   
			//this.lazyDataModel = this.pedidoService.pesquisar();
		}
	}
	
	public FormaPagamento[] formasPagemento() {
		return FormaPagamento.values();
	}

	public void buscarCidades(SelectEvent<EnderecoBean> event) {
		var estadoId = (event.getObject() != null) ? String.valueOf(event.getObject()) : "0";
		this.pedido.getEnderecoEntrega().setEstado(this.estadoService.buscarPorId(Long.parseLong(estadoId)));
	}
	
	public List<Cidade> listarCidades(){
		if(this.pedido.getEnderecoEntrega().getEstado().getId() != null){
			return this.cidadeService.listar(this.pedido.getEnderecoEntrega().getEstado().getId());
		}
		return null;
	}
	
	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public LazyDataModel<Pedido> getLazyDataModel() {
		return lazyDataModel;
	}

	public void setLazyDataModel(LazyDataModel<Pedido> lazyDataModel) {
		this.lazyDataModel = lazyDataModel;
	}

}