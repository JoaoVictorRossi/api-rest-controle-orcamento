package com.challenge.alura.orcamento.api.records;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosReceita(
		
		@NotBlank(message = "Campo não informado.")
		String descricao,
		
		@NotNull(message = "Campo não informado.")
		@Min(message = "O valor cadastrado não pode ser nagativo", value = 0)
		BigDecimal valor,
		
		@NotNull(message = "Campo não informado.")
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		LocalDate tempo) {

}
