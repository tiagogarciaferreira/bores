package com.tgfcodes.bores.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.springframework.beans.factory.annotation.Autowired;

import com.tgfcodes.bores.model.Usuario;
import com.tgfcodes.bores.security.Security;
import com.tgfcodes.bores.service.PedidoService;

@RequestScoped
@Named("dashboardGraficoBean")
public class DashboardGraficoBean {

	private LineChartModel lineChartModel;
	@Autowired
	private PedidoService pedidoService;
	@Autowired
	private Security security;
	
	@PostConstruct
	public void inicializar() {
		this.criarGrafico();
	}

	public LineChartModel getLineChartModel() {
		return lineChartModel;
	}

	private void criarGrafico() {
		this.lineChartModel = new LineChartModel();
		this.lineChartModel.setTitle("Pedidos");
		this.lineChartModel.setLegendPosition("e");
		this.lineChartModel.setAnimate(true);
		this.adicionarSerie("Todos os pedidos", null);
		Usuario usuario = this.security.getUsuarioLogado().getUsuario();
		this.adicionarSerie("Meus pedidos", usuario);
	}

	private void adicionarSerie(String rotulo, Usuario vendedor) {
		LineChartSeries series = new LineChartSeries(rotulo);
		Map<LocalDate, BigDecimal> pedidosPorData = this.pedidoService.pedidosGrafico(15, vendedor);
		for (LocalDate data : pedidosPorData.keySet()) {
			series.set(data, pedidosPorData.get(data));
		}
		this.lineChartModel.addSeries(series);
		this.lineChartModel.getAxis(AxisType.Y).setMin(0);
		this.lineChartModel.getAxis(AxisType.X).setMin(0);
		this.lineChartModel.getAxis(AxisType.Y).setLabel("Valores");
		DateAxis axis = new DateAxis("Períodos");
		axis.setTickFormat("%d/%m");
		this.lineChartModel.getAxes().put(AxisType.X, axis);
	}
}
