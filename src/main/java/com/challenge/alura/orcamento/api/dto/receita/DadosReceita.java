package com.challenge.alura.orcamento.api.dto.receita;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public abstract class DadosReceita {
	
	private String descricao;
	
	@Min(message = "O valor cadastrado não pode ser nagativo", value = 0)
	private BigDecimal valor;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate tempo;
	
}
