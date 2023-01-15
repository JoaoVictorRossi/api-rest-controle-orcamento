package com.challenge.alura.orcamento.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.alura.orcamento.api.dto.token.TokenDTO;
import com.challenge.alura.orcamento.api.dto.usuario.DadosAuthentication;
import com.challenge.alura.orcamento.api.model.Usuario;
import com.challenge.alura.orcamento.api.services.TokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class UsuarioController {
	
	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private TokenService service;
	
	@PostMapping
	public ResponseEntity<?> login(@RequestBody @Valid DadosAuthentication dados) {
		var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.pass());
		var authentication = manager.authenticate(authenticationToken);
		TokenDTO token = new TokenDTO(service.generateToken((Usuario) authentication.getPrincipal()));
		return ResponseEntity.ok(token);
	}

}
