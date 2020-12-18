package com.tgfcodes.bores.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.component.UIOutput;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.tgfcodes.bores.messages.Mensagem;
import com.tgfcodes.bores.model.Cidade;
import com.tgfcodes.bores.model.ItemPedido;
import com.tgfcodes.bores.model.Pedido;
import com.tgfcodes.bores.model.Produto;
import com.tgfcodes.bores.model.enumeration.FormaPagamento;
import com.tgfcodes.bores.model.enumeration.StatusPedido;
import com.tgfcodes.bores.service.CidadeService;
import com.tgfcodes.bores.service.EstadoService;
import com.tgfcodes.bores.service.PedidoService;
import com.tgfcodes.bores.service.ProdutoService;
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
	@Autowired
	private ProdutoService produtoService;
	private LazyDataModel<Pedido> lazyDataModel;
	private Produto produtoEditavel = new Produto();
	private Pedido pedido;
	private static final String PAGINA = "/pedido/PesquisaPedido.xhtml?faces-redirect=true";

	@PostConstruct
	public void inicializar() {
		this.verificarAcao();
	}
	
	public String salvar() {
		this.pedido.removerItemVazio();
		this.pedidoService.salvar(this.pedido);
		Mensagem.infoFlash("Pedido salvo com sucesso.");
		this.novo();
		return PAGINA;
	}
	
	public String emitir() {
		this.pedido.removerItemVazio();
		this.pedidoService.emitir(this.pedido);
		Mensagem.infoFlash("Pedido emitido com sucesso.");
		this.novo();
		return PAGINA;
	}

	public String cancelar() {
		this.pedidoService.cancelar(this.pedido);
		Mensagem.infoFlash("Pedido cancelado com sucesso.");
		this.novo();
		return PAGINA;
	}
	
	public String enviarPorEmail() {
		this.pedidoService.enviarEmail(this.pedido);
		Mensagem.infoFlash("Pedido enviado com sucesso.");
		return PAGINA;
	}
	
	public void novo() {
		this.pedido = new Pedido();
		this.pedido.adicionarItemVazio();
	}

	private void verificarAcao() {
		var pedidoId = FacesUtil.getParameter("pedido");
		this.novo();
		if (pedidoId != null) {
			this.pedido = this.pedidoService.buscarPorId(Long.parseLong(pedidoId));
		} else {
			// TODO: verificar porque quando for um novo registro tambem caira aqui pois o
			this.lazyDataModel = this.pedidoService.pesquisar();
		}
	}

	public void carregarProdutoEditavel(SelectEvent<PedidoBean> event) {
		ItemPedido item = this.pedido.getItens().get(0);
		if (this.produtoEditavel.getId() != null) {
			if (this.existeItemToProduto(this.produtoEditavel)) {
				this.produtoEditavel = new Produto();
				Mensagem.alert("Este produto já esta adicionado ao pedido.");
			} else {
				this.produtoEditavel = (event != null) ? this.produtoService.buscarPorId(this.produtoEditavel.getId()) : this.produtoEditavel;
				item.setProduto(this.produtoEditavel);
				item.setValorUnitario(this.produtoEditavel.getValorUnitario());
				this.pedido.adicionarItemVazio();
				this.produtoEditavel = new Produto();
				this.pedido.recalcularValorTotal();
			}
		}
	}
	
	public void carregarProdutoSku(AjaxBehaviorEvent event) {
		var sku = (String) ((UIOutput) event.getSource()).getValue();
		if (StringUtils.hasText(sku)) {
			this.produtoEditavel = this.produtoService.buscarPorSku(sku);
			this.carregarProdutoEditavel(null);
		}
	}
	
	public void updateQuantidade(ItemPedido item, int linha) {
		if(item.getQuantidade() < 1) {
			if(linha == 0) {
				item.setQuantidade(1);
			}
			else {
				this.pedido.getItens().remove(linha);
			}
		}
		this.pedido.recalcularValorTotal();
	}

	private boolean existeItemToProduto(Produto novoProduto) {// ok
		boolean existeItem = false;
		for (ItemPedido item : this.pedido.getItens()) {
			if (novoProduto.equals(item.getProduto())) {
				existeItem = true;
				break;
			}
		}
		return existeItem;
	}

	public void buscarCidades(SelectEvent<EnderecoBean> event) {
		var estadoId = (event.getObject() != null) ? String.valueOf(event.getObject()) : "0";
		this.pedido.getEnderecoEntrega().setEstado(this.estadoService.buscarPorId(Long.parseLong(estadoId)));
	}

	public List<Cidade> listarCidades() {
		if (this.pedido.getEnderecoEntrega().getEstado().getId() != null) {
			return this.cidadeService.listar(this.pedido.getEnderecoEntrega().getEstado().getId());
		}
		return null;
	}

	public FormaPagamento[] formasPagemento() {
		return FormaPagamento.values();
	}

	public StatusPedido[] statusPedidos() {
		return StatusPedido.values();
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Produto getProdutoEditavel() {
		return produtoEditavel;
	}

	public void setProdutoEditavel(Produto produtoEditavel) {
		this.produtoEditavel = produtoEditavel;
	}

	public LazyDataModel<Pedido> getLazyDataModel() {
		return lazyDataModel;
	}

	public void setLazyDataModel(LazyDataModel<Pedido> lazyDataModel) {
		this.lazyDataModel = lazyDataModel;
	}

}