package com.challenge.alura.orcamento.api.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.alura.orcamento.api.dto.resumo.BalanceCategorie;
import com.challenge.alura.orcamento.api.dto.resumo.DadosResumo;
import com.challenge.alura.orcamento.api.model.Despesa;
import com.challenge.alura.orcamento.api.model.Receita;

@Service
public class ResumoService {
	
	@Autowired
	private DespesaService despesaService;
	
	@Autowired
	public ResumoService(DespesaService despesaService) {
		this.despesaService = despesaService;
	}

	public List<BalanceCategorie> getValuePerCategorie(Integer mes, Integer ano) {
		return despesaService.getValuePerCategorie(ano, mes);
	}
	
	public BigDecimal calculateBalanceReceita(List<Receita> receitas) {
		if (receitas.size() != 0) {
			return receitas.stream().map(Receita::getValor).reduce(BigDecimal::add).get();			
		}
		return null;
	}
	
	public BigDecimal calculateBalanceDespesa(List<Despesa> despesas) {
		if (despesas.size() != 0) {
			return despesas.stream().map(Despesa::getValor).reduce(BigDecimal::add).get();			
		}
		return null;
	}
	
	public DadosResumo getResumo(BigDecimal balanceReceita, BigDecimal balanceDespesa, List<BalanceCategorie> balanceCategories) {
		
		balanceReceita = balanceReceita == null ? BigDecimal.ZERO : balanceReceita;
		balanceDespesa = balanceDespesa == null ? BigDecimal.ZERO : balanceDespesa;
		
		BigDecimal finalBalance = balanceReceita.subtract(balanceDespesa).setScale(2);
		return new DadosResumo(balanceReceita, balanceDespesa, finalBalance, balanceCategories);
		
	}
	
}
