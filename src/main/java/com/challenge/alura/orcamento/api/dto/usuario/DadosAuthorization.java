package com.challenge.alura.orcamento.api.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DadosAuthorization(
		
		@NotBlank
		@Email
		String login,
		
		@NotBlank
		String pass
		) {
	
	

}
