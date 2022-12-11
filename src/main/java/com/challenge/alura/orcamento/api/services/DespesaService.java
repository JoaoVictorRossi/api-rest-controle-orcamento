package com.challenge.alura.orcamento.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.alura.orcamento.api.dto.Dados;
import com.challenge.alura.orcamento.api.dto.DadosCriacaoRegistro;
import com.challenge.alura.orcamento.api.exceptions.DuplicatedPostRequestException;
import com.challenge.alura.orcamento.api.model.Despesa;
import com.challenge.alura.orcamento.api.repositories.DespesaRepository;

@Service
public class DespesaService {
	
	@Autowired
	private DespesaRepository repository;
	
	public Despesa save(DadosCriacaoRegistro dados) {
		isDuplicatedDespesa(dados);
		Despesa despesa = repository.save(new Despesa(dados));
		return despesa;
	}
	
	private void isDuplicatedDespesa(Dados dados) {
		if(dados.getTempo() != null) {
			Despesa despesa = repository.findByTempoMes(dados.getTempo().getMonthValue(), dados.getDescricao());
			if(despesa != null) {
				throw new DuplicatedPostRequestException("Cadastro de despesa duplicado.");
			}
		}
	}

}
