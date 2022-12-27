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

import com.challenge.alura.orcamento.api.dto.despesa.DadosAtualizacaoDespesa;
import com.challenge.alura.orcamento.api.dto.despesa.DadosCriacaoDespesa;
import com.challenge.alura.orcamento.api.dto.despesa.DadosDespesa;
import com.challenge.alura.orcamento.api.dto.resumo.BalanceCategorie;
import com.challenge.alura.orcamento.api.enums.Categoria;
import com.challenge.alura.orcamento.api.exceptions.DuplicatedPostRequestException;
import com.challenge.alura.orcamento.api.model.Despesa;
import com.challenge.alura.orcamento.api.repositories.DespesaRepository;

@SpringBootTest
class DespesaServiceTest {

	private DespesaService service;
	private DespesaRepository repository;
	
	private List<DadosDespesa> despesasDTO;
	private List<Despesa> despesas;
	
	@BeforeEach
	public void setup() {
		this.repository  = mock(DespesaRepository.class);
		this.service = new DespesaService(this.repository);
		
		despesasDTO = List.of(
				
				new DadosCriacaoDespesa(
						"Internet bill", 
						new BigDecimal("150.00"), 
						LocalDate.now(), 
						Categoria.MORADIA),
				
				new DadosCriacaoDespesa(
						"Get out", 
						new BigDecimal("150.00"), 
						LocalDate.now(), 
						null),
				
				new DadosCriacaoDespesa(
						"Workout", 
						new BigDecimal("150.00"), 
						LocalDate.now(), 
						Categoria.LAZER)
				
				);
		
		this.despesas = List.of(
				new Despesa(this.despesasDTO.get(0)),
				new Despesa(this.despesasDTO.get(1)),
				new Despesa(this.despesasDTO.get(2))
				);
	}
	
	@Test
	public void shouldReturnDespesaAndSave() {
		
		DadosCriacaoDespesa dto = (DadosCriacaoDespesa) despesasDTO.get(0);
		
		when(repository.save(any())).thenReturn(new Despesa(dto));
		
		Despesa despesa = service.save(dto);
		
		assertEquals("Internet bill", despesa.getDescricao());
		Mockito.verify(repository).save(new Despesa(dto));
		
	}
	
	@Test
	public void shouldSetDefaultCategoria_WhenCategoriaIsEmpty() {
		
		DadosCriacaoDespesa dto = (DadosCriacaoDespesa) despesasDTO.get(1);
		
		when(repository.save(any())).thenReturn(new Despesa(dto));
		
		Despesa despesa = service.save(dto);
		
		assertEquals(Categoria.OUTRAS, despesa.getCategoria());
		Mockito.verify(repository).save(new Despesa(dto));
		
	}
	
	@Test
	public void whenSaveDespesaDiplicated_ThenDuplicatedException() {
		
		DadosCriacaoDespesa dto = (DadosCriacaoDespesa) despesasDTO.get(0);
		
		when(repository.findByTempoMes(anyInt(), anyInt(), anyString()))
				.thenReturn(new Despesa(dto));
		
		var exception = assertThrows(DuplicatedPostRequestException.class, 
				() -> service.save(dto));
		
		assertEquals("Cadastro de despesa duplicado.", exception.getMessage());
	}
	
	@Test
	public void shouldReturnAllDespesas() {
		
		when(repository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(despesas));
		
		List<Despesa> listTest = service.findAll(Pageable.unpaged()).toList();
		
		assertEquals(3, listTest.size());
		
	}
	
	@Test
	public void shouldReturnDespesaByID() {
		
		when(repository.findById(anyLong())).thenReturn(Optional.of(despesas.get(2)));
		
		Despesa despesa = service.findById(5L);
		
		assertEquals("Workout", despesa.getDescricao());
		
	}
	
	@Test
	public void shouldReturnDespesaByDescription() {

		List<Despesa> despesasFilteredSize = this.despesas.stream()
		.filter(despesa -> despesa.getDescricao().toLowerCase().contains("get out")).toList();
		
		when(repository.findByDescricao("get out")).thenReturn(despesasFilteredSize);
		
		List<Despesa> despesasTest = service.findByDescricao("get out").toList();
		
		assertEquals(1, despesasTest.size());
	}
	
	@Test
	public void shouldUpdateEntityDespesa() {
		Despesa despesa = new Despesa(this.despesasDTO.get(2));
		DadosAtualizacaoDespesa dados = new DadosAtualizacaoDespesa("Test", null, null, Categoria.SAUDE);
		
		when(repository.getReferenceById(5L)).thenReturn(despesa);
		
		service.update(dados, 5L);
		
		assertEquals("Test", despesa.getDescricao());
		assertEquals(Categoria.SAUDE, despesa.getCategoria());
	}
	
	@Test
	public void whenUpdateDespesa_ThenDuplicatedException() {
		DadosAtualizacaoDespesa dados = new DadosAtualizacaoDespesa("Test", null, LocalDate.now(), Categoria.SAUDE);
		
		when(repository.findByTempoMes(anyInt(), anyInt(), anyString())).thenReturn(despesas.get(0));
		
		var exception = assertThrows(DuplicatedPostRequestException.class, () -> service.update(dados, 5L));
		assertEquals("Cadastro de despesa duplicado.", exception.getMessage());
	}
	
	@Test
	public void shouldReturnListOfBalanceCategories() {
		
		List<BalanceCategorie> balanceCategories = List.of(
				
				new BalanceCategorie(Categoria.OUTRAS, new BigDecimal("100.00")),
				new BalanceCategorie(Categoria.ALIMENTACAO, new BigDecimal("110.00")),
				new BalanceCategorie(Categoria.IMPREVISTOS, new BigDecimal("120.00"))
				);
		
		when(repository.findBalanceTotalPerCategorie(2022, 12)).thenReturn(balanceCategories);
		
		
		List<BalanceCategorie> listTest = service.getValuePerCategorie(2022, 12);
		assertEquals(Categoria.ALIMENTACAO, listTest.get(1).getCategoria());
	}
}
