package com.tgfcodes.bores.repository.helper.cliente;

import org.primefaces.model.LazyDataModel;

import com.tgfcodes.bores.model.Cliente;

public interface ClienteQueries {

	public LazyDataModel<Cliente> pesquisar();
}
