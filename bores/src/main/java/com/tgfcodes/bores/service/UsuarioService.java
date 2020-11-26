package com.tgfcodes.bores.service;

import java.util.List;

import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tgfcodes.bores.exception.NegocioException;
import com.tgfcodes.bores.model.Grupo;
import com.tgfcodes.bores.model.Usuario;
import com.tgfcodes.bores.repository.GrupoRepository;
import com.tgfcodes.bores.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Transactional(readOnly = false)
	public void salvar(Usuario usuario) {
		if(usuario.getId() == null) {
			throw new NegocioException("Email: Já esta cadastrado para outro usuário.");
		}
		this.usuarioRepository.save(usuario);
	}
	
	@Transactional(readOnly = false)
	public void excluir(Usuario usuario) {
		usuario = this.buscarPorId(usuario.getId());
		//TODO: não pode excluir se pertencer a algum pedido
		this.usuarioRepository.delete(usuario);
	}
	
	@Transactional(readOnly = false)
	public void updateStatus(Long usuarioId, Boolean status) {
		Usuario usuario = this.buscarPorId(usuarioId);
		usuario.setAtivo(status);
		this.usuarioRepository.save(usuario);
	}
	
	@Transactional(readOnly = true)
	public List<Usuario> buscarPorGrupo(Boolean status, String nameGrupo){
		Grupo grupo = this.grupoRepository.findByNomeIgnoreCase(nameGrupo);
		return this.usuarioRepository.buscarPorGrupo(status, grupo.getId());
	}
	
	@Transactional(readOnly = true)
	public LazyDataModel<Usuario> pesquisar(){
		return this.usuarioRepository.pesquisar();
	}
	
	@Transactional(readOnly = true)
	public Usuario buscarPorId(Long id) {
		return this.usuarioRepository.findById(id).get();
	}
	
}
