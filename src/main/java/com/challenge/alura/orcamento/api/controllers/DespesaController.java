package com.challenge.alura.orcamento.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.alura.orcamento.api.dto.DadosCriacaoRegistro;
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
	public ResponseEntity<Despesa> cadastrarDespesa(@RequestBody @Valid DadosCriacaoRegistro dados) {
		Despesa despesa = service.save(dados);
		return ResponseEntity.ok().body(despesa);
	}
	
	@GetMapping
	public Page<Despesa> listarDespesas(@PageableDefault(sort = {"tempo"}) Pageable pageable) {
		return service.findAll(pageable);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Despesa> listarDespesa(@PathVariable Long id) {
		Despesa despesa = service.findById(id);
		return ResponseEntity.ok().body(despesa);
	}

}
