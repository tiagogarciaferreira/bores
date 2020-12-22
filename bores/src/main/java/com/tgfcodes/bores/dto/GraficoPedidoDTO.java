package com.tgfcodes.bores.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class GraficoPedidoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private LocalDate data;
	private BigDecimal valor = BigDecimal.ZERO;

	public GraficoPedidoDTO(LocalDate data, BigDecimal valor) {
		this.data = data;
		this.valor = valor;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

}
