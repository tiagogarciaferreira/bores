package com.tgfcodes.bores.service;

import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tgfcodes.bores.model.Produto;
import com.tgfcodes.bores.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Transactional(readOnly = false)
	public void salvar(Produto produto) {
		this.produtoRepository.save(produto);
	}
	
	@Transactional(readOnly = false)
	public void excluir(Produto produto) {
		produto = this.buscarPorId(produto.getId());
		this.produtoRepository.delete(produto);
	}
	
	@Transactional(readOnly = true)
	public LazyDataModel<Produto> pesquisar(){
		return this.produtoRepository.pesquisar();
	}
	
	@Transactional(readOnly = true)
	public Produto buscarPorId(Long id) {
		return this.produtoRepository.findById(id).get();
	}

}
