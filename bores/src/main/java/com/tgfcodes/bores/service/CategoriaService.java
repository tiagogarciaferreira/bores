package com.tgfcodes.bores.service;

import java.util.List;

import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tgfcodes.bores.exception.NegocioException;
import com.tgfcodes.bores.model.Categoria;
import com.tgfcodes.bores.repository.CategoriaRepository;
import com.tgfcodes.bores.repository.SubcategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private SubcategoriaRepository subcategoriaRepository;
	
	@Transactional(readOnly = false)
	public void salvar(Categoria categoria) {
		this.categoriaRepository.save(categoria);
	}
	
	@Transactional(readOnly = false)
	public void excluir(Categoria categoria) {
		boolean remover =  this.subcategoriaRepository.existsByCategoria(categoria);
		if(remover) {
			throw new NegocioException("Categoria: Não pode ser excluída. Possue subcategorias associadas.");
		}
		categoria = this.buscarPorId(categoria.getId());
		this.categoriaRepository.delete(categoria);
	}
	
	@Transactional(readOnly = true)
	public LazyDataModel<Categoria> pesquisar(){
		return this.categoriaRepository.pesquisar();
	}
	
	@Transactional(readOnly = true)
	public List<Categoria> listar(){
		return this.categoriaRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Categoria buscarPorId(Long id) {
		return this.categoriaRepository.findById(id).get();
	}
	
}
