package com.challenge.alura.orcamento.api.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.challenge.alura.orcamento.api.model.Usuario;

@Service
public class TokenService {
	
	@Value("${api.security.token.secret}")
	private String secret;
	
	public String generateToken(Usuario user) {
		try {
		    Algorithm algorithm = Algorithm.HMAC256(secret);
		    return JWT.create()
		        .withIssuer("orcamento.api")
		        .withSubject(user.getLogin())
		        .withExpiresAt(dateLimite())
		        .sign(algorithm);
		} catch (JWTCreationException exception){
		    throw new RuntimeException("Error generate token");
		}
	}
	
	public String getSubject(String tokenJWT) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
		    return JWT.require(algorithm)
		        .withIssuer("orcamento.api")
		        .build()
		        .verify(tokenJWT)
		        .getSubject();
		} catch (JWTVerificationException exception){
		    throw new RuntimeException("Token JWT is invalid or expired.");
		}
	}

	private Instant dateLimite() {
		return LocalDateTime.now().plusMonths(1L).toInstant(ZoneOffset.of("-03:00"));
	}

}
