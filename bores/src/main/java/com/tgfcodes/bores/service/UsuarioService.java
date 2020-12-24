package com.tgfcodes.bores.service;

import java.util.List;

import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tgfcodes.bores.exception.NegocioException;
import com.tgfcodes.bores.model.Grupo;
import com.tgfcodes.bores.model.Usuario;
import com.tgfcodes.bores.repository.GrupoRepository;
import com.tgfcodes.bores.repository.PedidoRepository;
import com.tgfcodes.bores.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private GrupoRepository grupoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Transactional(readOnly = false)
	public void salvar(Usuario usuario) {
		Usuario usuarioExistente = this.usuarioRepository.findByEmailIgnoreCase(usuario.getEmail());
		if(usuarioExistente != null && !usuario.equals(usuarioExistente)) {
			throw new NegocioException("Email já esta cadastrado.");
		}
		if(usuario.getId() == null && !StringUtils.hasText(usuario.getSenha())) {
			throw new NegocioException("Senha obrigatória a novos usuário.");
		}
		if (usuario.getId() == null || !StringUtils.hasText(usuario.getSenha())) {
			usuario.setSenha(this.passwordEncoder.encode(usuario.getSenha()));
		} else if (StringUtils.hasText(usuario.getSenha())) {
			usuario.setSenha(usuarioExistente.getSenha());
		}
		this.usuarioRepository.save(usuario);
	}
	
	@Transactional(readOnly = false)
	public void excluir(Usuario usuario) {
		boolean remover = this.pedidoRepository.existsByVendedor(usuario);
		if(remover) {
			throw new NegocioException("Usuário(Vendedor) não pode ser excluído. Possue pedidos associados.");
		}
		usuario = this.buscarPorId(usuario.getId());
		this.usuarioRepository.delete(usuario);
	}
	
	@Transactional(readOnly = false)
	public void updateStatus(Long usuarioId, Boolean status) {
		Usuario usuario = this.buscarPorId(usuarioId);
		usuario.setAtivo(status);
		this.usuarioRepository.save(usuario);
	}
	
	@Transactional(readOnly = false)
	public void alterarSenha(Usuario usuario) {
		Usuario usuarioAtual = this.usuarioRepository.findByEmailIgnoreCase(usuario.getEmail());
		usuarioAtual.setSenha(passwordEncoder.encode(usuario.getSenha()));
		this.usuarioRepository.save(usuarioAtual);
	}
	
	@Transactional(readOnly = true)
	public List<Usuario> buscarPorGrupo(Boolean status, String nameGrupo){
		Grupo grupo = this.grupoRepository.findByNomeIgnoreCase(nameGrupo);
		return this.usuarioRepository.porGrupo(status, grupo.getId());
	}
	
	@Transactional(readOnly = true)
	public LazyDataModel<Usuario> pesquisar(){
		return this.usuarioRepository.pesquisar();
	}
	
	@Transactional(readOnly = true)
	public Usuario buscarPorId(Long id) {
		return this.usuarioRepository.findById(id).get();
	}
	
	@Transactional(readOnly = true)
	public Long contarVendedores() {
		Grupo grupo = this.grupoRepository.findByNomeIgnoreCase("Vendedor");
		return this.usuarioRepository.contar(grupo);
	}
}
