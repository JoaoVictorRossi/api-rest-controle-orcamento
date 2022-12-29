package com.challenge.alura.orcamento.api.dto.resumo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DadosResumo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private BigDecimal totalReceitas;
	private BigDecimal totalDespesas;
	private BigDecimal balance;
	
	private List<BalanceCategorie> balanceCategories;

}
