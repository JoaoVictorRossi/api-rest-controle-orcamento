package com.challenge.alura.orcamento.api.exceptions;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.challenge.alura.orcamento.api.exceptions.resources.Erro;

@ControllerAdvice()
public class ResourceExceptionHandler {
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handlerValidationRequest(MethodArgumentNotValidException e) {
		Map<String, String> erros = new HashMap<>();
		
		e.getBindingResult().getAllErrors().forEach((error) -> {
				String fieldName = ((FieldError) error).getField();
				String errorMessage = error.getDefaultMessage();
				
				erros.put(fieldName, errorMessage);
				
		});
		return new ResponseEntity<>(erros, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Erro> handlerResourceNotFound(ResourceNotFoundException e) {
		String erro = "Recurso não encontrado";
		HttpStatus status = HttpStatus.NOT_FOUND;
		Erro err = new Erro(Instant.now(), status.value(), erro, e.getMessage());
		return ResponseEntity.status(status).body(err);
		
	}
	
	@ExceptionHandler(DuplicatedPostRequestException.class)
	public ResponseEntity<Erro> handlerDuplicatedResource(DuplicatedPostRequestException e) {
		String erro = "Recurso duplicado.";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Erro err = new Erro(Instant.now(), status.value(), erro, e.getMessage());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(InvalidDataAccessApiUsageException.class)
	public ResponseEntity<Erro> handlerDataAccess(InvalidDataAccessApiUsageException e) {
		String erro = "Campo obrigatório não informado.";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Erro err = new Erro(Instant.now(), status.value(), erro, e.getMessage());
		return ResponseEntity.status(status).body(err);
	}
	

}
