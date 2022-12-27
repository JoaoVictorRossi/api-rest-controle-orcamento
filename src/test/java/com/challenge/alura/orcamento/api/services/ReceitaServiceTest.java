package com.challenge.alura.orcamento.api.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.challenge.alura.orcamento.api.dto.receita.DadosAtualizacaoReceita;
import com.challenge.alura.orcamento.api.dto.receita.DadosCriacaoReceita;
import com.challenge.alura.orcamento.api.dto.receita.DadosReceita;
import com.challenge.alura.orcamento.api.exceptions.DuplicatedPostRequestException;
import com.challenge.alura.orcamento.api.model.Receita;
import com.challenge.alura.orcamento.api.repositories.ReceitaRepository;

@SpringBootTest
class ReceitaServiceTest {

	private ReceitaService service;
	private ReceitaRepository repository;
	
	private List<DadosReceita> ReceitasDTO;
	private List<Receita> Receitas;
	
	@BeforeEach
	public void setup() {
		this.repository  = mock(ReceitaRepository.class);
		this.service = new ReceitaService(this.repository);
		
		ReceitasDTO = List.of(
				
				new DadosCriacaoReceita(
						"Internet bill", 
						new BigDecimal("150.00"), 
						LocalDate.now()),
				
				new DadosCriacaoReceita(
						"Get out", 
						new BigDecimal("150.00"), 
						LocalDate.now()),
				
				new DadosCriacaoReceita(
						"Workout", 
						new BigDecimal("150.00"), 
						LocalDate.now())
				
				);
		
		this.Receitas = List.of(
				new Receita(this.ReceitasDTO.get(0)),
				new Receita(this.ReceitasDTO.get(1)),
				new Receita(this.ReceitasDTO.get(2))
				);
	}
	
	@Test
	public void shouldReturnReceitaAndSave() {
		
		DadosCriacaoReceita dto = (DadosCriacaoReceita) ReceitasDTO.get(0);
		
		when(repository.save(any())).thenReturn(new Receita(dto));
		
		Receita Receita = service.save(dto);
		
		assertEquals("Internet bill", Receita.getDescricao());
		Mockito.verify(repository).save(new Receita(dto));
		
	}
	
	@Test
	public void whenSaveReceitaDiplicated_ThenDuplicatedException() {
		
		DadosCriacaoReceita dto = (DadosCriacaoReceita) ReceitasDTO.get(0);
		
		when(repository.findByTempoMes(anyInt(), anyInt(), anyString()))
				.thenReturn(new Receita(dto));
		
		var exception = assertThrows(DuplicatedPostRequestException.class, 
				() -> service.save(dto));
		
		assertEquals("Cadastro de receita duplicado.", exception.getMessage());
	}
	
	@Test
	public void shouldReturnAllReceitas() {
		
		when(repository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(Receitas));
		
		List<Receita> listTest = service.findAll(Pageable.unpaged()).toList();
		
		assertEquals(3, listTest.size());
		
	}
	
	@Test
	public void shouldReturnReceitaByID() {
		
		when(repository.findById(anyLong())).thenReturn(Optional.of(Receitas.get(2)));
		
		Receita Receita = service.findById(5L);
		
		assertEquals("Workout", Receita.getDescricao());
		
	}
	
	@Test
	public void shouldReturnReceitaByDescription() {

		List<Receita> ReceitasFilteredSize = this.Receitas.stream()
		.filter(Receita -> Receita.getDescricao().toLowerCase().contains("get out")).toList();
		
		when(repository.findByDescricao("get out")).thenReturn(ReceitasFilteredSize);
		
		List<Receita> ReceitasTest = service.findByDescricao("get out").toList();
		
		assertEquals(1, ReceitasTest.size());
	}
	
	@Test
	public void shouldUpdateEntityReceita() {
		Receita receita = new Receita(this.ReceitasDTO.get(2));
		DadosAtualizacaoReceita dados = new DadosAtualizacaoReceita("Test", new BigDecimal("50.00"), null);
		
		when(repository.getReferenceById(5L)).thenReturn(receita);
		
		service.update(dados, 5L);
		
		assertEquals("Test", receita.getDescricao());
		assertEquals(new BigDecimal("50.00"), receita.getValor());
	}
	
	@Test
	public void whenUpdateReceita_ThenDuplicatedException() {
		DadosAtualizacaoReceita dados = new DadosAtualizacaoReceita("Test", null, LocalDate.now());
		
		when(repository.findByTempoMes(anyInt(), anyInt(), anyString())).thenReturn(Receitas.get(0));
		
		var exception = assertThrows(DuplicatedPostRequestException.class, () -> service.update(dados, 5L));
		assertEquals("Cadastro de receita duplicado.", exception.getMessage());
	}
}