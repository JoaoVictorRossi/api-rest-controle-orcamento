package com.challenge.alura.orcamento.api.exceptions;

public class DuplicatedPostRequestException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public DuplicatedPostRequestException(String msg) {
		super(msg);
	}

}
