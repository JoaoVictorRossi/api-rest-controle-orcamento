package com.challenge.alura.orcamento.api.dto.receita;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DadosAtualizacaoReceita extends DadosReceita{

	public DadosAtualizacaoReceita(
			
			
			String descricao,
		
			BigDecimal valor,
			
			LocalDate tempo
			
			) {
		super(descricao, valor, tempo);
	}

}
