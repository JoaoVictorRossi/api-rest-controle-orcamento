package com.challenge.alura.orcamento.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DadosAtualizacaoRegistro extends Dados{

	public DadosAtualizacaoRegistro(
			
			
			String descricao,
		
			BigDecimal valor,
			
			LocalDate tempo
			
			) {
		super(descricao, valor, tempo);
	}

}
