package com.tgfcodes.bores.repository.helper.pedido;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import org.primefaces.model.LazyDataModel;

import com.tgfcodes.bores.dto.EstatisticasPedidoDTO;
import com.tgfcodes.bores.model.Pedido;
import com.tgfcodes.bores.model.Usuario;
import com.tgfcodes.bores.model.enumeration.StatusPedido;

public interface PedidoQueries {

	public LazyDataModel<Pedido> pesquisar();
	public Map<LocalDate, BigDecimal> pedidosGrafico(Integer numeroDias, Usuario usuario);
	public EstatisticasPedidoDTO totalPedidosPorStatus(StatusPedido status);
}
