package com.challenge.alura.orcamento.api.dto.resumo;

import java.math.BigDecimal;

import com.challenge.alura.orcamento.api.enums.Categoria;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BalanceCategorie {

	private Categoria categoria;
	private BigDecimal valor;
}
