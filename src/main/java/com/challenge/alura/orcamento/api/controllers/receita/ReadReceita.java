package com.challenge.alura.orcamento.api.controllers.receita;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.alura.orcamento.api.model.Receita;
import com.challenge.alura.orcamento.api.services.ReceitaService;

@RestController
@RequestMapping("/receitas")
public class ReadReceita {
	
	@Autowired
	private ReceitaService service;
	
	@GetMapping
	public Page<Receita> listar(@PageableDefault(sort = {"tempo"}) Pageable pageable) {
		return service.findAll(pageable);
	}

}
