package com.challenge.alura.orcamento.api.dto.despesa;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.challenge.alura.orcamento.api.enums.Categoria;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public abstract class DadosDespesa {
	
	private String descricao;
	
	@Min(message = "O valor cadastrado não pode ser nagativo", value = 0)
	private BigDecimal valor;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate tempo;
	
	private Categoria categoria;

}
