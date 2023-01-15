package com.challenge.alura.orcamento.api.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.alura.orcamento.api.dto.usuario.DadosAuthorization;
import com.challenge.alura.orcamento.api.services.AuthorizationService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth/registro")
public class AuthorizationController {
	
	@Autowired
	private AuthorizationService service;
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> registrar(@RequestBody @Valid DadosAuthorization dados) {
		service.save(dados);
		return ResponseEntity.ok(dados);
	}

}
