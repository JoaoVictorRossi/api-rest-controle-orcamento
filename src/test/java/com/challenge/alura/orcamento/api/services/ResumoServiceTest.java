package com.challenge.alura.orcamento.api.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.challenge.alura.orcamento.api.dto.despesa.DadosCriacaoDespesa;
import com.challenge.alura.orcamento.api.dto.receita.DadosCriacaoReceita;
import com.challenge.alura.orcamento.api.dto.resumo.BalanceCategorie;
import com.challenge.alura.orcamento.api.dto.resumo.DadosResumo;
import com.challenge.alura.orcamento.api.enums.Categoria;
import com.challenge.alura.orcamento.api.model.Despesa;
import com.challenge.alura.orcamento.api.model.Receita;

class ResumoServiceTest {

	private DespesaService despesaService;
	private ResumoService resumoService;
	
	@BeforeEach
	public void setup() {
		this.despesaService = mock(DespesaService.class);
		this.resumoService = new ResumoService(this.despesaService);
	}
	
	@Test
	public void shouldReturnListOfBalanceCategories() {
		
		List<BalanceCategorie> balanceCategories = List.of(
				
				new BalanceCategorie(Categoria.OUTRAS, new BigDecimal("100.00")),
				new BalanceCategorie(Categoria.ALIMENTACAO, new BigDecimal("110.00")),
				new BalanceCategorie(Categoria.IMPREVISTOS, new BigDecimal("120.00"))
				);
		
		when(resumoService.getValuePerCategorie(2022, 12)).thenReturn(balanceCategories);
		
		
		List<BalanceCategorie> listTest = resumoService.getValuePerCategorie(2022, 12);
		assertEquals(Categoria.ALIMENTACAO, listTest.get(1).getCategoria());
	}
	
	@Test
	public void shouldReturnTotalDespesaBalance() {
		
		List<Despesa> despesas = List.of(
				new Despesa(new DadosCriacaoDespesa("Test1", new BigDecimal("1000.00"), LocalDate.now(), null)),
				new Despesa(new DadosCriacaoDespesa("Test2", new BigDecimal("1000.00"), LocalDate.now(), null)),
				new Despesa(new DadosCriacaoDespesa("Test3", new BigDecimal("1000.00"), LocalDate.now(), null))
				);
		
		BigDecimal value = resumoService.calculateBalanceDespesa(despesas);
		assertEquals(new BigDecimal("3000.00"), value);
		
	}

	@Test
	public void shouldReturnTotalReceitaBalance() {
		
		List<Receita> receitas = List.of(
				new Receita(new DadosCriacaoReceita("Test1", new BigDecimal("2000.00"), LocalDate.now())),
				new Receita(new DadosCriacaoReceita("Test2", new BigDecimal("2000.00"), LocalDate.now())),
				new Receita(new DadosCriacaoReceita("Test3", new BigDecimal("2000.00"), LocalDate.now()))
				);
		
		BigDecimal value = resumoService.calculateBalanceReceita(receitas);
		assertEquals(new BigDecimal("6000.00"), value);
		
	}
	
	@Test
	public void returnSummaryOfTheMonth() {
		
		List<BalanceCategorie> totalByCategoria = List.of(
	            new BalanceCategorie(Categoria.ALIMENTACAO, BigDecimal.valueOf(1250.0)),
	            new BalanceCategorie(Categoria.SAUDE, BigDecimal.valueOf(350.0)),
	            new BalanceCategorie(Categoria.TRANSPORTE, BigDecimal.valueOf(2000.0)),
	            new BalanceCategorie(Categoria.OUTRAS, BigDecimal.valueOf(8900.0))
	        );
		
		DadosResumo summay = resumoService.getResumo(new BigDecimal("630.00"), new BigDecimal("1090.00"), totalByCategoria);
		
		assertEquals(new BigDecimal("-460.00"), summay.getBalance());
		assertEquals(4, summay.getBalanceCategories().size());
		
	}
}
