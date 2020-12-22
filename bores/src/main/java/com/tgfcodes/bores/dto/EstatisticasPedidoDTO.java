package com.tgfcodes.bores.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class EstatisticasPedidoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long quantidade;
	private BigDecimal valor = BigDecimal.ZERO;

	public EstatisticasPedidoDTO(Long quantidade, BigDecimal valor) {
		this.quantidade = quantidade;
		this.valor = valor;
	}

	public Long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getValor() {
		return (this.valor == null) ? BigDecimal.ZERO.setScale(2) : this.valor.setScale(2);
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

}
