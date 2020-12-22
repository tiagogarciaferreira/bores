package com.tgfcodes.bores.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tgfcodes.bores.model.Usuario;
import com.tgfcodes.bores.repository.helper.usuario.UsuarioQueries;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>, UsuarioQueries {

	@Query(value = "from Usuario u inner join fetch u.grupos g where u.ativo = ?1 and g.id = ?2")
	public List<Usuario> porGrupo(Boolean ativo, Long grupoId);
	public Usuario findByEmailIgnoreCase(String email);
}
