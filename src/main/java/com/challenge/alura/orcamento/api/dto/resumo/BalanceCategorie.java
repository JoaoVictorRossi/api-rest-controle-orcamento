package com.challenge.alura.orcamento.api.dto.resumo;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;

public interface BalanceCategorie {

	@Value("#{target.categoria.descricao}")
	String getCategoria();
	BigDecimal getTotal();
	
}
