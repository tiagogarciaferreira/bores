package com.tgfcodes.bores.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tgfcodes.bores.model.PasswordReset;
import com.tgfcodes.bores.model.Usuario;

@Repository
public interface PasswordResetRepository extends JpaRepository<PasswordReset, Long>{

	public PasswordReset findByUsuario(Usuario usuario);
	public PasswordReset findByToken(String token);
	public boolean existsByToken(String token);
}
