package com.challenge.alura.orcamento.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.challenge.alura.orcamento.api.exceptions.DuplicatedPostRequestException;
import com.challenge.alura.orcamento.api.model.Receita;
import com.challenge.alura.orcamento.api.records.DadosReceita;
import com.challenge.alura.orcamento.api.repositories.ReceitaRepository;

@Service
public class ReceitaService {
	
	@Autowired
	private ReceitaRepository repository;
	
	
	public Receita save(DadosReceita dados) {
		isReceitaDuplicated(dados);
		return repository.save(new Receita(dados));
	}
	
	public Page<Receita> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}
	
	private void isReceitaDuplicated(DadosReceita dados) {
		Receita receita = repository.findByTempoMes(dados.tempo().getMonthValue(), dados.descricao());
		if (receita != null) {
			throw new DuplicatedPostRequestException("Cadastro de receita duplicado.");
		}
	}

}
