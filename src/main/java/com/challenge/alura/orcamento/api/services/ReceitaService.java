package com.challenge.alura.orcamento.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.challenge.alura.orcamento.api.dto.receita.DadosAtualizacaoReceita;
import com.challenge.alura.orcamento.api.dto.receita.DadosCriacaoReceita;
import com.challenge.alura.orcamento.api.dto.receita.DadosReceita;
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
	
	public Page<Receita> findByDescricao(String descricao) {
		List<Receita> receitasByDescricao = repository.findByDescricao(descricao);
		Page<Receita> page = new PageImpl<>(receitasByDescricao);
		return page;
	}
	
	public void update(DadosAtualizacaoReceita dados, Long id) {
		isReceitaDuplicated(dados);
		try {
			Receita receita = repository.getReferenceById(id);
			receita.atualizarInformacoes(dados);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}
	
	public void deleteById(Long id) {
		repository.deleteById(id);
	}
	
	
	
	private void isReceitaDuplicated(DadosReceita dados) {
		if (dados.getTempo() != null) {
			Receita receita = repository.findByTempoMes(
					dados.getTempo().getMonthValue(),
					dados.getTempo().getYear(), 
					dados.getDescricao()); 
			if (receita != null) {
				throw new DuplicatedPostRequestException("Cadastro de receita duplicado.");
			}			
		}
	}


}
