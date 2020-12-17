package com.tgfcodes.bores.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tgfcodes.bores.model.Categoria;
import com.tgfcodes.bores.repository.helper.categoria.CategoriaQueries;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long>, CategoriaQueries {

	
}
