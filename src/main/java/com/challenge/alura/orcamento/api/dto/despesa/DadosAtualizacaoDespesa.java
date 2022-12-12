package com.challenge.alura.orcamento.api.dto.despesa;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.challenge.alura.orcamento.api.enums.Categoria;

public class DadosAtualizacaoDespesa extends DadosDespesa {
	
	public DadosAtualizacaoDespesa(
			
			
			String descricao,
		
			BigDecimal valor,
			
			LocalDate tempo,
			
			Categoria categoria
			
			) {
		super(descricao, valor, tempo, categoria);
	}

}
