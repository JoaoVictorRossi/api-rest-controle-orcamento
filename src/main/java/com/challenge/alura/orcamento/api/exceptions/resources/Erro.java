package com.challenge.alura.orcamento.api.exceptions.resources;

import java.io.Serializable;
import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Erro implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Instant timestamp;
	private Integer status;
	private String erro;
	private String mensagem;
	
}
