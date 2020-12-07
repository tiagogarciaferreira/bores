package com.tgfcodes.bores.service;

import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tgfcodes.bores.exception.NegocioException;
import com.tgfcodes.bores.model.Produto;
import com.tgfcodes.bores.repository.ProdutoRepository;
import com.tgfcodes.bores.repository.SubcategoriaRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private SubcategoriaRepository subcategoriaRepository;
	//@Autowired
	//private PedidoRepository pedidoRepository;

	@Transactional(readOnly = false)
	public void salvar(Produto produto) {
		produto.setSubcategoria((produto.getId() == null) ? this.subcategoriaRepository.getOne(produto.getSubcategoria().getId()) : produto.getSubcategoria());
		this.produtoRepository.save(produto);
	}
	
	@Transactional(readOnly = false)
	public void excluir(Produto produto) {
		boolean remover = false;
		if(remover) {
			throw new NegocioException("Produto: Não pode ser excluído. Possue pedidos associados.");
		}
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
