package com.challenge.alura.orcamento.api.exceptions;

public class ResourceNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundException(Long id) {
		super("Recurso com o id " + id + " não encontrado.");
	}

}
