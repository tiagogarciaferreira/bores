package com.tgfcodes.bores.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

@RequestScoped
@Named("graficoPedidosBean")
public class GraficoPedidosBean {

	private CartesianChartModel chartModel;

	@PostConstruct
	public void inicializar() {
		this.chartModel = new CartesianChartModel();
		this.adicionarSerie("Todos os pedidos");
		this.adicionarSerie("Meus pedidos");
	}

	private void adicionarSerie(String rotulo) {
		ChartSeries series = new ChartSeries(rotulo);
		series.set("1", Math.random() * 1000);
		series.set("2", Math.random() * 1000);
		series.set("3", Math.random() * 1000);
		series.set("4", Math.random() * 1000);
		series.set("5", Math.random() * 1000);
		this.chartModel.addSeries(series);
	}

	public CartesianChartModel getChartModel() {
		return chartModel;
	}

}
