package com.challenge.alura.orcamento.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.challenge.alura.orcamento.api.dto.Dados;
import com.challenge.alura.orcamento.api.dto.DadosAtualizacaoReceita;
import com.challenge.alura.orcamento.api.dto.DadosCriacaoReceita;
import com.challenge.alura.orcamento.api.exceptions.DuplicatedPostRequestException;
import com.challenge.alura.orcamento.api.exceptions.ResourceNotFoundException;
import com.challenge.alura.orcamento.api.model.Receita;
import com.challenge.alura.orcamento.api.repositories.ReceitaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ReceitaService {
	
	@Autowired
	private ReceitaRepository repository;
	
	
	public Receita save(DadosCriacaoReceita dados) {
		isReceitaDuplicated(dados);
		return repository.save(new Receita(dados));
	}
	
	public Page<Receita> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}
	
	public Receita findById(Long id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public void update(DadosAtualizacaoReceita dados) {
		isReceitaDuplicated(dados);
		try {
			Receita receita = repository.getReferenceById(dados.getId());
			receita.atualizarInformacoes(dados);	
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(dados.getId());
		}
	}
	
	public Receita delete(Long id) {
		Receita receita = findById(id);
		repository.deleteById(id);
		return receita;
		
	}
	
	private void isReceitaDuplicated(Dados dados) {
		if (dados.getTempo() != null) {
			Receita receita = repository.findByTempoMes(dados.getTempo().getMonthValue(), dados.getDescricao());
			if (receita != null) {
				throw new DuplicatedPostRequestException("Cadastro de receita duplicado.");
			}			
		}
	}


}
