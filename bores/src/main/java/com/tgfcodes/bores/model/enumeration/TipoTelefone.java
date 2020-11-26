package com.tgfcodes.bores.model.enumeration;

public enum TipoTelefone {

	MOVEL("Movel"), 
	FIXO("Fixo");

	private String descricao;

	TipoTelefone(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static TipoTelefone verificarTipo(String telefone) {
		return telefone.length() > 14 ? MOVEL : FIXO;
	}

}
