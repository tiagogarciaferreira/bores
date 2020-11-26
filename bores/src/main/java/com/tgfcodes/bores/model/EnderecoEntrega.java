package com.tgfcodes.bores.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class EnderecoEntrega extends Endereco implements Serializable {

	private static final long serialVersionUID = 1L;

	public EnderecoEntrega() {
		super();
	}

}
