package com.tgfcodes.bores.service;

import java.util.List;

import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tgfcodes.bores.model.Categoria;
import com.tgfcodes.bores.model.Subcategoria;
import com.tgfcodes.bores.repository.CategoriaRepository;
import com.tgfcodes.bores.repository.SubcategoriaRepository;

@Service
public class SubcategoriaService {

	@Autowired
	private SubcategoriaRepository subcategoriaRepository;
	@Autowired
	private CategoriaRepository categoriaRepository; 
	
	@Transactional(readOnly = false)
	public void salvar(Subcategoria subcategoria) {
		this.subcategoriaRepository.save(subcategoria);
	}
	
	@Transactional(readOnly = false)
	public void excluir(Subcategoria subcategoria) {
		subcategoria = subcategoriaRepository.findById(subcategoria.getId()).get();
		this.subcategoriaRepository.delete(subcategoria);
	}
	
	@Transactional(readOnly = true)
	public List<Subcategoria> listar(Long categoriaId){
		Categoria categoria = this.categoriaRepository.findById(categoriaId).get();
		return this.subcategoriaRepository.findByCategoria(categoria);
	}
	
	@Transactional(readOnly = true)
	public LazyDataModel<Subcategoria> pesquisar(){
		return this.subcategoriaRepository.pesquisar();
	}
	
	@Transactional(readOnly = true)
	public Subcategoria buscarPorId(Long subcategoriaId) {
		return this.subcategoriaRepository.findById(subcategoriaId).get();
	} 
	
}
