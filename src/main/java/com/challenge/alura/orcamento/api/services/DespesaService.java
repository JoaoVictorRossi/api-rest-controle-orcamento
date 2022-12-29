package com.challenge.alura.orcamento.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.challenge.alura.orcamento.api.dto.despesa.DadosAtualizacaoDespesa;
import com.challenge.alura.orcamento.api.dto.despesa.DadosCriacaoDespesa;
import com.challenge.alura.orcamento.api.dto.despesa.DadosDespesa;
import com.challenge.alura.orcamento.api.dto.resumo.BalanceCategorie;
import com.challenge.alura.orcamento.api.exceptions.DuplicatedPostRequestException;
import com.challenge.alura.orcamento.api.exceptions.ResourceNotFoundException;
import com.challenge.alura.orcamento.api.model.Despesa;
import com.challenge.alura.orcamento.api.repositories.DespesaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DespesaService {
	
	@Autowired
	private DespesaRepository repository;
	
	@Autowired
	public DespesaService(DespesaRepository repository) {
		this.repository = repository;
	}
	
	public Despesa save(DadosCriacaoDespesa dados) {
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
	
	public Page<Despesa> findByDescricao(String descricao) {
		List<Despesa> despesasByDescricao = repository.findByDescricao(descricao);
		Page<Despesa> page = new PageImpl<>(despesasByDescricao);
		return page;
	}
	
	public Page<Despesa> findAllByTempo(Integer ano, Integer mes) {
		return new PageImpl<>(repository.findAllByTempo(mes, ano));
	}
	
	public Despesa update(DadosAtualizacaoDespesa dados, long id) {
		isDuplicatedDespesa(dados);
		try {
			Despesa despesa = repository.getReferenceById(id);
			despesa.atualizarInformacoes(dados);
			return repository.saveAndFlush(despesa);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}
	
	public void deleteById(Long id) {
		findById(id);
		repository.deleteById(id);
	}
	
	public List<BalanceCategorie> getValuePerCategorie(Integer ano, Integer mes) {
		return repository.findBalanceTotalPerCategorie(ano, mes);
	}
	
	private void isDuplicatedDespesa(DadosDespesa dados) {
		if(dados.getTempo() != null) {
			Despesa despesa = repository.findByTempoMes(
					dados.getTempo().getMonthValue(), 
					dados.getTempo().getYear(),
					dados.getDescricao());
			if(despesa != null) {
				throw new DuplicatedPostRequestException("Cadastro de despesa duplicado.");
			}
		}
	}





}
