package com.challenge.alura.orcamento.api.controllers.receita;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.alura.orcamento.api.model.Receita;
import com.challenge.alura.orcamento.api.records.DadosReceita;
import com.challenge.alura.orcamento.api.services.ReceitaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/receitas")
public class InsertReceita {
	
	@Autowired
	private ReceitaService service;
	
	@PostMapping
	public ResponseEntity<Receita> cadastrar(@RequestBody @Valid DadosReceita dados) {
		Receita receita = service.save(dados);
		return ResponseEntity.ok().body(receita);
	}
	
}
