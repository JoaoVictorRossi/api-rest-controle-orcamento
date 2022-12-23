package com.challenge.alura.orcamento.api.enums;

public enum Categoria {
	
	ALIMENTACAO("Alimentação"), 
	SAUDE("Saúde"), 
	MORADIA("Moradia"), 
	TRANSPORTE("Transporte"), 
	EDUCACAO("Educação"), 
	LAZER("Lazer"), 
	IMPREVISTOS("Imprevistos"), 
	OUTRAS("Outras");
	
	private final String NOME_CATEGORIA;
	
	Categoria(String descricao) {
		this.NOME_CATEGORIA = descricao;
	}
	
	public String getDescricao() {
		return this.NOME_CATEGORIA;
	}
}
