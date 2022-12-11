package com.challenge.alura.orcamento.api.controllers;

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
import org.springframework.web.bind.annotation.RestController;

import com.challenge.alura.orcamento.api.dto.DadosAtualizacaoRegistro;
import com.challenge.alura.orcamento.api.dto.DadosCriacaoRegistro;
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
	public ResponseEntity<Receita> cadastrar(@RequestBody @Valid DadosCriacaoRegistro dados) {
		Receita receita = service.save(dados);
		return ResponseEntity.ok().body(receita);
	}
	
	@GetMapping
	public Page<Receita> listar(@PageableDefault(sort = {"tempo"}) Pageable pageable) {
		return service.findAll(pageable);
	}
	
	@GetMapping(value =  "/{id}")
	public ResponseEntity<Receita> buscarReceita(@PathVariable Long id) {
		Receita receita = service.findById(id);
		return ResponseEntity.ok().body(receita);
	}
	
	@PutMapping(value = "/{id}")
	@Transactional
	public void atualizarReceita(@RequestBody DadosAtualizacaoRegistro dados, @PathVariable long id) {
		service.update(dados, id);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Receita> deletarReceita(@PathVariable Long id) {
		Receita receita = service.delete(id);
		return ResponseEntity.ok().body(receita);
	}
	 
}
