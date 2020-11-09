package com.tgfcodes.bores.repository.helper.categoria;

import org.primefaces.model.LazyDataModel;
import com.tgfcodes.bores.model.Categoria;

public interface CategoriaQueries {

	public LazyDataModel<Categoria> pesquisar();
}
