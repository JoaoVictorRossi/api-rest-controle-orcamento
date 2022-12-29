package com.challenge.alura.orcamento.api.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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

import com.challenge.alura.orcamento.api.dto.receita.DadosAtualizacaoReceita;
import com.challenge.alura.orcamento.api.dto.receita.DadosCriacaoReceita;
import com.challenge.alura.orcamento.api.model.Receita;
import com.challenge.alura.orcamento.api.services.ReceitaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {

	@Autowired
	private ReceitaService service;

	
	@PostMapping
	@Transactional
	public ResponseEntity<Receita> cadastrar(@RequestBody @Valid DadosCriacaoReceita dados, UriComponentsBuilder uriBuilder) {
		Receita receita = service.save(dados);
		URI uri = uriBuilder.path("/receitas/{id}").buildAndExpand(receita.getId()).toUri();
		return ResponseEntity.created(uri).body(receita);
	}
	
	@GetMapping
	public ResponseEntity<Page<Receita>> listar(@PageableDefault(sort = {"tempo"}) Pageable pageable, 
			@RequestParam(required = false) String descricao) {
		Page<Receita> page;
		if(descricao == null) {
			page = service.findAll(pageable);			
		} else {
			page = service.findByDescricao(descricao);
		}
		return ResponseEntity.ok(page);			
	}
	
	@GetMapping(value =  "/{id}")
	public ResponseEntity<Receita> buscarReceita(@PathVariable Long id) {
		Receita receita = service.findById(id);
		return ResponseEntity.ok(receita);
	}
	
	@GetMapping(value = "/{ano}/{mes}")
	public ResponseEntity<Page<Receita>> listarPorData(@PathVariable Integer ano, @PathVariable Integer mes) {
		Page<Receita> page = service.findAllByTempo(ano, mes);
		return ResponseEntity.ok(page);
	}
	
	@PutMapping(value = "/{id}")
	@Transactional
	public ResponseEntity<?> atualizarReceita(@RequestBody DadosAtualizacaoReceita dados, @PathVariable Long id) {
		Receita receita = service.update(dados, id);
		return ResponseEntity.ok(receita);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deletarReceita(@PathVariable Long id) {
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	 
}
