package com.challenge.alura.orcamento.api.dto.despesa;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.challenge.alura.orcamento.api.enums.Categoria;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DadosCriacaoDespesa extends DadosDespesa{
	
	@NotBlank(message = "Campo não informado.")
	private String descricao;
	
	@NotNull(message = "Campo não informado.")
	private BigDecimal valor;
	
	@NotNull(message = "Campo não informado.")
	private LocalDate tempo;
	
	public DadosCriacaoDespesa(
			
			String descricao,
			
			BigDecimal valor,
			
			LocalDate tempo,
			
			Categoria categoria
			) {
		super(descricao, valor, tempo, categoria);
		this.descricao = descricao;
		this.valor = valor;
		this.tempo = tempo;
	}

}
