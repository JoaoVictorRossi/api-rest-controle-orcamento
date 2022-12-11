package com.challenge.alura.orcamento.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.challenge.alura.orcamento.api.dto.Dados;
import com.challenge.alura.orcamento.api.dto.DadosAtualizacaoRegistro;
import com.challenge.alura.orcamento.api.dto.DadosCriacaoRegistro;
import com.challenge.alura.orcamento.api.exceptions.DuplicatedPostRequestException;
import com.challenge.alura.orcamento.api.exceptions.ResourceNotFoundException;
import com.challenge.alura.orcamento.api.model.Despesa;
import com.challenge.alura.orcamento.api.repositories.DespesaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DespesaService {
	
	@Autowired
	private DespesaRepository repository;
	
	public Despesa save(DadosCriacaoRegistro dados) {
		isDuplicatedDespesa(dados);
		Despesa despesa = repository.save(new Despesa(dados));
		return despesa;
	}
	
	public Page<Despesa> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}
	
	public Despesa findById(Long id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public void update(DadosAtualizacaoRegistro dados, long id) {
		isDuplicatedDespesa(dados);
		try {
			Despesa despesa = repository.getReferenceById(id);
			despesa.atualizarInformacoes(dados);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
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
