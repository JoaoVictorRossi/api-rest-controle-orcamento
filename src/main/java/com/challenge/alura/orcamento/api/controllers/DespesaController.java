package com.challenge.alura.orcamento.api.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.challenge.alura.orcamento.api.dto.despesa.DadosAtualizacaoDespesa;
import com.challenge.alura.orcamento.api.dto.despesa.DadosCriacaoDespesa;
import com.challenge.alura.orcamento.api.model.Despesa;
import com.challenge.alura.orcamento.api.services.DespesaService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/despesas")
public class DespesaController {
	
	@Autowired
	private DespesaService service;
	
	@PostMapping
	@Transactional
	public ResponseEntity<Despesa> cadastrarDespesa(@RequestBody @Valid DadosCriacaoDespesa dados, UriComponentsBuilder uriBuilder) {
		Despesa despesa = service.save(dados);
		URI uri = uriBuilder.path("/despesas/{id}").buildAndExpand(despesa.getId()).toUri();
		return ResponseEntity.created(uri).body(despesa);
	}
	
	@GetMapping
	public ResponseEntity<Page<Despesa>> listarDespesas(@PageableDefault(sort = {"tempo"}) Pageable pageable,  
			@RequestParam(required = false) String descricao) {
		Page<Despesa> page;			
		if (descricao == null) {
			page = service.findAll(pageable);		
		} else {
			page = service.findByDescricao(descricao);
		}
		return ResponseEntity.ok(page);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Despesa> listarDespesa(@PathVariable Long id) {
		Despesa despesa = service.findById(id);
		return ResponseEntity.ok(despesa);
	}
	
	@GetMapping(value = "/{ano}/{mes}")
	public ResponseEntity<Page<Despesa>> listarPorData(@PathVariable Integer ano, @PathVariable Integer mes) {
		Page<Despesa> page = service.findAllByTempo(ano, mes);
		return ResponseEntity.ok(page);
	}
	
	@PutMapping(value = "/{id}")
	@Transactional
	public ResponseEntity<?> atualizarDespesa(@RequestBody DadosAtualizacaoDespesa dados, @PathVariable Long id) {
		service.update(dados, id);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deletarReceita(@PathVariable Long id) {
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
