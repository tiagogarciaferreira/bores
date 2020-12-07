package com.tgfcodes.bores.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tgfcodes.bores.model.Categoria;
import com.tgfcodes.bores.model.Subcategoria;
import com.tgfcodes.bores.repository.helper.subcategoria.SubcategoriaQueries;

@Repository
public interface SubcategoriaRepository extends JpaRepository<Subcategoria, Long>, SubcategoriaQueries {

	public List<Subcategoria> findByCategoria(Categoria categoria);
	public boolean existsByCategoria(Categoria categoria);
}
