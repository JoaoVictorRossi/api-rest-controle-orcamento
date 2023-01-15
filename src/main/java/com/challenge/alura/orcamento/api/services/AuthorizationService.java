package com.challenge.alura.orcamento.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.challenge.alura.orcamento.api.dto.usuario.DadosAuthorization;
import com.challenge.alura.orcamento.api.model.Usuario;
import com.challenge.alura.orcamento.api.repositories.UsuarioRepository;

@Service
public class AuthorizationService {
	
	@Autowired
	private UsuarioRepository repository;
	
	public Usuario save(DadosAuthorization dados) { 
		dados = new DadosAuthorization(dados.login(), hashPass(dados.pass()));
		return repository.save(new Usuario(dados));
	}
	
	private BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	private String hashPass(String pass) {
		return passwordEncoder().encode(pass);
	}

}
