package com.challenge.alura.orcamento.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public class DadosAtualizacaoReceita extends Dados{
	
	@NotNull
	private Long id;

	public DadosAtualizacaoReceita(
			
			Long id,
			
			String descricao,
		
			BigDecimal valor,
			
			LocalDate tempo
			
			) {
		super(id, descricao, valor, tempo);
		this.id = id;
	}

}
