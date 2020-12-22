package com.tgfcodes.bores.repository.helper.produto;

import org.primefaces.model.LazyDataModel;

import com.tgfcodes.bores.dto.EstatisticasEstoqueDTO;
import com.tgfcodes.bores.model.Produto;

public interface ProdutoQueries {

	public LazyDataModel<Produto> pesquisar();
	public EstatisticasEstoqueDTO totalEstoque();

}
