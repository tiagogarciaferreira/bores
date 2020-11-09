package com.tgfcodes.bores.repository.helper.cidade;

import org.primefaces.model.LazyDataModel;
import com.tgfcodes.bores.model.Cidade;

public interface CidadeQueries {

	public LazyDataModel<Cidade> pesquisar();
}
