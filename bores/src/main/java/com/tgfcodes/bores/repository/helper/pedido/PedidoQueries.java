package com.tgfcodes.bores.repository.helper.pedido;

import org.primefaces.model.LazyDataModel;

import com.tgfcodes.bores.model.Pedido;

public interface PedidoQueries {

	public LazyDataModel<Pedido> pesquisar();
}
