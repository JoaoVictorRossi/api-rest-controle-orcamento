package com.challenge.alura.orcamento.api.dto.receita;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DadosCriacaoReceita extends DadosReceita{
	
	@NotBlank(message = "Campo não informado.")
	private String descricao;
	
	@NotNull(message = "Campo não informado.")
	private BigDecimal valor;
	
	@NotNull(message = "Campo não informado.")
	private LocalDate tempo;
	
	public DadosCriacaoReceita(
			
			String descricao,
			
			BigDecimal valor,
			
			LocalDate tempo
			) {
		super(descricao, valor, tempo);
		this.descricao = descricao;
		this.valor = valor;
		this.tempo = tempo;
	}

}
