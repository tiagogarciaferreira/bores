package com.tgfcodes.bores.service;

import java.util.List;

import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tgfcodes.bores.exception.NegocioException;
import com.tgfcodes.bores.model.Produto;
import com.tgfcodes.bores.repository.ItemPedidoRepository;
import com.tgfcodes.bores.repository.ProdutoRepository;
import com.tgfcodes.bores.repository.SubcategoriaRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private SubcategoriaRepository subcategoriaRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository; 

	@Transactional(readOnly = false)
	public void salvar(Produto produto) {
		produto.setSubcategoria((produto.getId() == null) ? this.subcategoriaRepository.getOne(produto.getSubcategoria().getId()) : produto.getSubcategoria());
		Produto produtoExistente = this.produtoRepository.findBySkuIgnoreCase(produto.getSku());
		if(produtoExistente != null && !produtoExistente.equals(produto)) {
			throw new NegocioException("SKU de produto já esta cadastrado.");
		}
		this.produtoRepository.save(produto);
	}
	
	@Transactional(readOnly = false)
	public void excluir(Produto produto) {
		boolean remover = this.itemPedidoRepository.existsByProduto(produto);
		if(remover) {
			throw new NegocioException("Produto não pode ser excluído. Possue pedidos associados.");
		}
		produto = this.buscarPorId(produto.getId());
		this.produtoRepository.delete(produto);
	}
	
	@Transactional(readOnly = true)
	public List<Produto> completar(String nome) {
		return (StringUtils.hasText(nome) ? this.produtoRepository.porNome(nome) : null);
	}
	
	@Transactional(readOnly = true)
	public LazyDataModel<Produto> pesquisar(){
		return this.produtoRepository.pesquisar();
	}
	
	@Transactional(readOnly = true)
	public Produto buscarPorId(Long id) {
		return this.produtoRepository.findById(id).get();
	}
	
	@Transactional(readOnly = true)
	public Produto buscarPorSku(String sku) {
		return this.produtoRepository.findBySkuIgnoreCase(sku);
	}

}
