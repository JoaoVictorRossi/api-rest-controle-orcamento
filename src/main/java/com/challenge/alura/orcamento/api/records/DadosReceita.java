package com.challenge.alura.orcamento.api.records;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosReceita(
		
		@NotBlank(message = "Campo (descrição) não informado.")
		String descricao,
		
		@NotNull(message = "Campo (valor) não informado.")
		@Min(message = "O valor cadastrado não pode ser nagativo", value = 0)
		BigDecimal valor,
		
		@NotNull(message = "Campo (Data) não informado.")
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		Date tempo) {

}
