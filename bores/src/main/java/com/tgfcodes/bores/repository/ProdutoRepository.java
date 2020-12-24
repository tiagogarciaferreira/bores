package com.tgfcodes.bores.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tgfcodes.bores.model.Produto;
import com.tgfcodes.bores.model.Subcategoria;
import com.tgfcodes.bores.repository.helper.produto.ProdutoQueries;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>, ProdutoQueries {

	public boolean existsBySubcategoria(Subcategoria subcategoria);
	public Produto findBySkuIgnoreCase(String sku);
	@Query("from Produto p where lower(p.nome) like lower(concat(?1,'%'))")
	public List<Produto> porNome(String nome);
	public List<Produto> findByQuantidadeEstoqueLessThan(Integer quantidadeEstoque);
	
}
