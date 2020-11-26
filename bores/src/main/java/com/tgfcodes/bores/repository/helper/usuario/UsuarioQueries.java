package com.tgfcodes.bores.repository.helper.usuario;

import org.primefaces.model.LazyDataModel;

import com.tgfcodes.bores.model.Usuario;

public interface UsuarioQueries {

	public LazyDataModel<Usuario> pesquisar();
	
}
