package com.challenge.alura.orcamento.api.dto.resumo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.challenge.alura.orcamento.api.model.Despesa;
import com.challenge.alura.orcamento.api.model.Receita;

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
	
	public DadosResumo(List<Receita> receitas, List<Despesa> despesas, List<BalanceCategorie> balanceCategories) {
		
		this.totalReceitas = new BigDecimal(receitas.stream().mapToDouble(
				receita -> receita.getValor().doubleValue()).sum());
		this.totalDespesas = new BigDecimal(despesas.stream().mapToDouble(
				despesa -> despesa.getValor().doubleValue()).sum());
		
		this.balance = this.totalReceitas.subtract(this.totalDespesas);
		
		this.balanceCategories = balanceCategories;
		
	}

}
