package com.tgfcodes.bores.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tgfcodes.bores.model.Grupo;
import com.tgfcodes.bores.repository.GrupoRepository;

@Service
public class GrupoService {

	@Autowired
	private GrupoRepository grupoRepository;

	@Transactional(readOnly = false)
	public void salvar(Grupo grupo) {
		this.grupoRepository.save(grupo);
	}

	@Transactional(readOnly = true)
	public List<Grupo> listar() {
		return this.grupoRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Grupo buscarPorId(Long id) {
		return this.grupoRepository.findById(id).get();
	}

}
