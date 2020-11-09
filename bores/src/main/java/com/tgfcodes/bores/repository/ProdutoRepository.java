package com.tgfcodes.bores.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tgfcodes.bores.model.Produto;
import com.tgfcodes.bores.repository.helper.produto.ProdutoQueries;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>, ProdutoQueries {

	
}
