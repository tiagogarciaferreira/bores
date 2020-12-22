package com.tgfcodes.bores.controller;

import java.util.Map;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;

import com.tgfcodes.bores.dto.EstatisticasEstoqueDTO;
import com.tgfcodes.bores.dto.EstatisticasPedidoDTO;
import com.tgfcodes.bores.model.enumeration.StatusPedido;
import com.tgfcodes.bores.service.ClienteService;
import com.tgfcodes.bores.service.EstoqueService;
import com.tgfcodes.bores.service.PedidoService;
import com.tgfcodes.bores.service.SubcategoriaService;
import com.tgfcodes.bores.service.UsuarioService;

@RequestScoped
@Named("dashboardCardBean")
public class DashboardCardBean {

	private Map<String, EstatisticasPedidoDTO> estatisticasPedidos;
	@Autowired
	private PedidoService pedidoService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private SubcategoriaService subcategoriaService;
	@Autowired
	private EstoqueService estoqueService;
	
	
	@PostConstruct
	public void inicializar() {
		this.iniciarEstatiscticas();
	}
	
	public EstatisticasPedidoDTO totalPedidos() {
		return this.estatisticasPedidos.get("TOTAL_PEDIDOS");
	}
	
	public EstatisticasPedidoDTO totalPedidosOrcamento() {
		return this.estatisticasPedidos.get(StatusPedido.ORCAMENTO.name());
	}
	
	public EstatisticasPedidoDTO totalPedidosEmitidos() {
		return this.estatisticasPedidos.get(StatusPedido.EMITIDO.name());
	}
	
	public EstatisticasPedidoDTO totalPedidosCancelados() {
		return this.estatisticasPedidos.get(StatusPedido.CANCELADO.name());
	}
	
	public EstatisticasEstoqueDTO totalEstoque() {
		return (EstatisticasEstoqueDTO) this.estatisticasPedidos.get("TOTAL_ESTOQUE");
	}
	
	public Long totalSubcategorias() {
		return this.subcategoriaService.contador();
	}
	
	public Long totalClientes() {
		return this.clienteService.contador();
	}

	public Long totalVendedores() {
		return this.usuarioService.contarVendedores();
	}

	private void iniciarEstatiscticas() {
		this.estatisticasPedidos = new TreeMap<String, EstatisticasPedidoDTO>();
		this.estatisticasPedidos.put(StatusPedido.ORCAMENTO.name(), this.pedidoService.totalPedidosPorStatus(StatusPedido.ORCAMENTO));
		this.estatisticasPedidos.put(StatusPedido.EMITIDO.name(), this.pedidoService.totalPedidosPorStatus(StatusPedido.EMITIDO));
		this.estatisticasPedidos.put(StatusPedido.CANCELADO.name(), this.pedidoService.totalPedidosPorStatus(StatusPedido.CANCELADO));
		this.estatisticasPedidos.put("TOTAL_PEDIDOS", this.pedidoService.totalPedidosPorStatus(null));
		this.estatisticasPedidos.put("TOTAL_ESTOQUE", this.estoqueService.totalEstoque());
	}

}
