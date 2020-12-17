package com.tgfcodes.bores.event;

import com.tgfcodes.bores.model.Pedido;

public class PedidoEvent {

	private Pedido pedido;

	public PedidoEvent(Pedido pedido) {
		this.pedido = pedido;
	}

	public Pedido getPedido() {
		return pedido;
	}

}
