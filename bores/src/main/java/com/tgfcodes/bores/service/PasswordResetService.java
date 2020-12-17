package com.tgfcodes.bores.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tgfcodes.bores.event.PasswordResetEvent;
import com.tgfcodes.bores.exception.NegocioException;
import com.tgfcodes.bores.model.PasswordReset;
import com.tgfcodes.bores.model.Usuario;
import com.tgfcodes.bores.repository.PasswordResetRepository;
import com.tgfcodes.bores.repository.UsuarioRepository;

@Service
public class PasswordResetService {

	@Autowired
	private PasswordResetRepository passwordResetRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Transactional(readOnly = false)
	public void salvar(PasswordReset passwordReset, String url) {
		Usuario usuario = this.usuarioRepository.findByEmailIgnoreCase(passwordReset.getUsuario().getEmail());
		if(usuario == null) {
			throw new NegocioException("Email não corresponde a nenhum usuário.");
		}
		PasswordReset passwordResetExistente = this.passwordResetRepository.findByUsuario(usuario);
		if(passwordResetExistente != null) {
			this.excluir(passwordResetExistente);
		}
		passwordReset.setUsuario(usuario);
		passwordReset = this.passwordResetRepository.save(passwordReset);
		publisher.publishEvent(new PasswordResetEvent(passwordReset, url));
	}
	
	@Transactional(readOnly = false)
	public void excluir(PasswordReset passwordReset) {
		passwordReset = this.buscarPorId(passwordReset.getId());
		this.passwordResetRepository.delete(passwordReset);
	}
	
	@Transactional(readOnly = true)
	public PasswordReset buscarPorId(Long id) {
		return this.passwordResetRepository.findById(id).get();
	}
	
	@Transactional(readOnly = true)
	public PasswordReset buscarPorToken(String token) {
		return this.passwordResetRepository.findByToken(token);
	}
	
	@Transactional(readOnly = true)
	public boolean isExisteToken(String token) {
		return this.passwordResetRepository.existsByToken(token);
	}

	@Transactional(readOnly = true)
	public PasswordReset validar(String token, boolean cancelar) {
		PasswordReset passwordReset = this.buscarPorToken(token);
		if(passwordReset == null || !passwordReset.isValido()) {
			return null;
		}
	    return this.buscarPorToken(token);
	}
	
	@Transactional(readOnly = false)
	public void cancelar(String token) {
		PasswordReset passwordReset = this.passwordResetRepository.findByToken(token);
		if(passwordReset != null) {
			this.excluir(passwordReset);
		}
	}

	@Transactional(readOnly = false)
	public void atualizar(PasswordReset passwordReset) {
		Usuario usuario = this.usuarioRepository.findByEmailIgnoreCase(passwordReset.getUsuario().getEmail());
		if(usuario == null || !usuario.getId().equals(passwordReset.getUsuario().getId())) {
			throw new NegocioException("Nenhum usuário foi encontrado.");
		}
		else if(usuario.getId().equals(passwordReset.getUsuario().getId())) {
			//TODO: passar encrypt quando colocar a segurança.
			usuario.setSenha(passwordReset.getUsuario().getSenha());
			this.usuarioRepository.save(usuario);
			this.excluir(passwordReset);
		}
	}
	
}
