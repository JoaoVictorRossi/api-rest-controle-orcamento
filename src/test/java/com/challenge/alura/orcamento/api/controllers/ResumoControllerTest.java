package com.challenge.alura.orcamento.api.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.challenge.alura.orcamento.api.dto.resumo.BalanceCategorie;
import com.challenge.alura.orcamento.api.dto.resumo.DadosResumo;
import com.challenge.alura.orcamento.api.enums.Categoria;
import com.challenge.alura.orcamento.api.services.ResumoService;

@SpringBootTest
@AutoConfigureMockMvc
class ResumoControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ResumoService service;

	@Test
	public void shouldReturn200AndReturnSummaryOfTheMonth() throws Exception {
		
		List<BalanceCategorie> totalByCategoria = List.of(
	            new BalanceCategorie(Categoria.ALIMENTACAO, BigDecimal.valueOf(1250.0)),
	            new BalanceCategorie(Categoria.SAUDE, BigDecimal.valueOf(350.0)),
	            new BalanceCategorie(Categoria.TRANSPORTE, BigDecimal.valueOf(2000.0)),
	            new BalanceCategorie(Categoria.OUTRAS, BigDecimal.valueOf(8900.0))
	        );
		
		DadosResumo summay = new DadosResumo(new BigDecimal("10000.00"), new BigDecimal("80000.00"), new BigDecimal("2000.00"), totalByCategoria);
		
		when(service.getResumo(any(BigDecimal.class), any(BigDecimal.class), anyList())).thenReturn(summay);
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/resumo/2022/12"))
		.andExpect(MockMvcResultMatchers
				.status()
				.isOk());
	}

}
