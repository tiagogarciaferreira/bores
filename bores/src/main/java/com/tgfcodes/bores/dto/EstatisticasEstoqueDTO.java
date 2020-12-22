package com.tgfcodes.bores.dto;

import java.math.BigDecimal;

public class EstatisticasEstoqueDTO extends EstatisticasPedidoDTO {

	private static final long serialVersionUID = 1L;

	public EstatisticasEstoqueDTO(Long quantidade, BigDecimal valor) {
		super(quantidade, valor);
	}

}
