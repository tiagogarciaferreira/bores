package com.tgfcodes.bores.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.tgfcodes.bores.messages.Mensagem;
import com.tgfcodes.bores.model.PasswordReset;
import com.tgfcodes.bores.service.PasswordResetService;
import com.tgfcodes.bores.util.FacesUtil;

@ViewScoped
@Named("passwordResetBean")
public class PasswordResetBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@Autowired
	private PasswordResetService passwordResetService;
	private PasswordReset passwordReset;

	@PostConstruct
	public void inicializar() {
		this.nova();
	}

	public void nova() {
		this.passwordReset = new PasswordReset();
	}

	public void salvar() {
		this.passwordResetService.salvar(this.passwordReset, FacesUtil.getUrlResetPassword());
		Mensagem.info("Verifique sua caixa de emails.");
		this.nova();
	}
	
	public void atualizar() {
		this.passwordResetService.atualizar(this.passwordReset);
		Mensagem.info("Senha atualizada com sucesso.");
		this.nova();
	}

	public void verificarAcao() {
		String token = FacesUtil.getParameter("token");
		boolean cancelar = Boolean.valueOf(FacesUtil.getParameter("cancelar"));
		if(StringUtils.hasText(token)) {
			if(!this.passwordResetService.isExisteToken(token)){
				Mensagem.error("Solicitação inválida."); 
			}
			else if(cancelar) {
				this.passwordResetService.cancelar(token);
				Mensagem.info("Solicitação cancelada com sucesso.");
			}
			else if(!cancelar){
				this.passwordReset = this.passwordResetService.validar(token, cancelar);
				if(this.passwordReset == null) {
					this.nova();
					Mensagem.error("Solicitação inválida ou expirada."); 
				}
				this.passwordReset = this.passwordResetService.buscarPorToken(token);
				this.passwordReset.getUsuario().setEmail("");
			}
		}
	}

	public PasswordReset getPasswordReset() {
		return passwordReset;
	}

	public void setPasswordReset(PasswordReset passwordReset) {
		this.passwordReset = passwordReset;
	}
}