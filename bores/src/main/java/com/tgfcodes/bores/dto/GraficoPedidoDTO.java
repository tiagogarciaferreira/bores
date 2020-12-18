package com.tgfcodes.bores.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class GraficoPedidoDTO {

	private LocalDate data;
	private BigDecimal valor = BigDecimal.ZERO;

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
