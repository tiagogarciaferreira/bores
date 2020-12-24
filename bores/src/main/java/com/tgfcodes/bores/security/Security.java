package com.tgfcodes.bores.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class Security {

	public UsuarioSistema getUsuarioLogado() {
		Authentication authentication = (Authentication) this.getAuthentication();
		UsuarioSistema usuarioLogado = (UsuarioSistema) authentication.getPrincipal();
		return usuarioLogado;
	}
	
	private Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
}
