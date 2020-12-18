package com.tgfcodes.bores.repository.helper.pedido;

import java.util.List;

import org.primefaces.model.LazyDataModel;

import com.tgfcodes.bores.dto.GraficoPedidoDTO;
import com.tgfcodes.bores.model.Pedido;

public interface PedidoQueries {

	public LazyDataModel<Pedido> pesquisar();
	public List<GraficoPedidoDTO> pedidosGrafico();
}
