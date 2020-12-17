package com.tgfcodes.bores.model.enumeration;

public enum FormaPagamento {

	DINHEIRO("Dinheiro"), 
	CARTAO_CREDITO("Cartão de crédito"), 
	CARTAO_DEBITO("Cartão de débito"), 
	CHEQUE("Cheque"),
	PIX("Pix"),
	BOLETO_BANCARIO("Boleto bancário"), 
	TRANSFERENCIA_BANCARIA("Transferência bancária"),
	DEPOSITO_BANCARIO("Depósito bancário");

	private String descricao;

	FormaPagamento(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
