package com.tgfcodes.bores.repository.helper.usuario;

import java.util.List;

import org.primefaces.model.LazyDataModel;

import com.tgfcodes.bores.model.Grupo;
import com.tgfcodes.bores.model.Usuario;

public interface UsuarioQueries {

	public LazyDataModel<Usuario> pesquisar();
	public Long contar(Grupo grupo);
	public List<String> permissoes(Usuario usuario);
}
