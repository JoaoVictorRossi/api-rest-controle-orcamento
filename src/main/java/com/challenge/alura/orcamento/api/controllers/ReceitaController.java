package com.challenge.alura.orcamento.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.alura.orcamento.api.model.Receita;
import com.challenge.alura.orcamento.api.records.DadosReceita;
import com.challenge.alura.orcamento.api.repositories.ReceitaRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {
	
	@Autowired
	private ReceitaRepository repository;
	
	@PostMapping
	public ResponseEntity<Receita> cadastrar(@RequestBody @Valid DadosReceita dados) {
		Receita receita = repository.save(new Receita(dados));
		return ResponseEntity.ok().body(receita);
	}
	
}
