package com.challenge.alura.orcamento.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.alura.orcamento.api.dto.resumo.BalanceCategorie;

@Service
public class ResumoService {
	
	@Autowired
	private DespesaService despesaService;
	
	public List<BalanceCategorie> getValuePerCategorie(Integer mes, Integer ano) {
		return despesaService.getValuePerCategorie(ano, mes);
	}
	
}
