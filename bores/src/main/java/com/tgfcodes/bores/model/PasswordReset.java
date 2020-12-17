package com.tgfcodes.bores.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "password_reset")
public class PasswordReset  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String token;
	@OneToOne(targetEntity = Usuario.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id")
	private Usuario usuario = new Usuario();
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name = "data_validade")
	private LocalDateTime dataValidade;
	@Transient
	private LocalDateTime dataSolicitacao;
	
	public PasswordReset() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public LocalDateTime getDataValidade() {
		return dataValidade;
	}

	public void setDataValidade(LocalDateTime dataValidade) {
		this.dataValidade = dataValidade;
	}
	
	public LocalDateTime getDataSolicitacao() {
		return dataSolicitacao;
	}
	
	public void setDataSolicitacao(LocalDateTime dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}

	public boolean isValido() {
		return this.id != null && LocalDateTime.now(ZoneId.of("America/Sao_Paulo")).isBefore(this.dataValidade) ;
	}

	@PrePersist
	private void prePersist() {
		this.dataValidade = LocalDateTime.now(ZoneId.of("America/Sao_Paulo")).plusHours(1L);
		this.token = UUID.randomUUID().toString();
	}
	
	@PostLoad
	private void postLoad() {
		this.dataSolicitacao = this.dataValidade.minusHours(1L);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PasswordReset other = (PasswordReset) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
