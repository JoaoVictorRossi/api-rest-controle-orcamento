package com.challenge.alura.orcamento.api.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.challenge.alura.orcamento.api.dto.despesa.DadosAtualizacaoDespesa;
import com.challenge.alura.orcamento.api.dto.despesa.DadosCriacaoDespesa;
import com.challenge.alura.orcamento.api.enums.Categoria;
import com.challenge.alura.orcamento.api.model.Despesa;
import com.challenge.alura.orcamento.api.services.DespesaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class DespesaControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;
	
	@MockBean
	private DespesaService service;

	private final String uriString = "/despesas";

	private List<DadosCriacaoDespesa> despesasDTO;
	private List<Despesa> despesas;

	@BeforeEach
	public void setup() {

		this.despesasDTO = List.of(

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
						LocalDate.of(2022, 10, 05), 
						Categoria.LAZER)

		);

		this.despesas = List.of(
				new Despesa(this.despesasDTO.get(0)), 
				new Despesa(this.despesasDTO.get(1)),
				new Despesa(this.despesasDTO.get(2)));
	}

	@Test
	public void shouldPostNewDespesa() throws JsonProcessingException, Exception {
		DadosCriacaoDespesa despesaDTO = this.despesasDTO.get(2);
		
		when(service.save(any())).thenReturn(this.despesas.get(2));

		mockMvc.perform(MockMvcRequestBuilders.post(uriString).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(despesaDTO)))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}
	
	@Test
	public void whenPostWithAnyParameterIsNull_thenBadRequestAndReturnValidation() throws JsonProcessingException, Exception {
		DadosCriacaoDespesa despesaDTO = new DadosCriacaoDespesa(
				"Test", null, LocalDate.now(), Categoria.LAZER);
		
		when(service.save(any())).thenReturn(this.despesas.get(2));
		
		mockMvc.perform(MockMvcRequestBuilders
				.post(uriString)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(despesaDTO)))
		.andExpect(MockMvcResultMatchers
				.status().isBadRequest());
	}

	@Test
	public void shouldReturnAllDespesasByDescription() throws Exception {
		
		List<Despesa> despesasFiltered = this.despesas.stream()
				.filter(despesa -> despesa.getDescricao().toLowerCase().contains("workout"))
				.toList();
		
		when(service.findByDescricao("workout")).thenReturn(new PageImpl<>(despesasFiltered));
		
		mockMvc.perform(MockMvcRequestBuilders.get(uriString + "?descricao=workout"))
				.andExpect(MockMvcResultMatchers.status().isOk());

	}
	
	@Test
	public void shouldReturnAllDespesas() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(uriString))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void shouldReturnDespesaById() throws Exception {
		
		when(service.findById(5L)).thenReturn(despesas.get(0));
		
		mockMvc.perform(MockMvcRequestBuilders
				.get(uriString + "/5")).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void shouldReturnListDespesasByDate() throws Exception {
		List<Despesa> filteredList = despesas.stream()
				.filter(despesas -> despesas.getTempo().getMonthValue() == 10 &&
				despesas.getTempo().getYear() == 2022).toList();
		
		when(service.findAllByTempo(anyInt(), anyInt())).thenReturn(new PageImpl<>(filteredList));
		
		mockMvc.perform(MockMvcRequestBuilders
				.get(uriString + "/2022/10"))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void shouldUpdateDespesaById() throws JsonProcessingException, Exception {
		
		
		
		DadosAtualizacaoDespesa dados = new DadosAtualizacaoDespesa("Test", null, null, Categoria.SAUDE);
		
		Despesa despesa = despesas.get(0);
		
		when(service.update(dados, 5)).thenReturn(despesa);
		
		mockMvc.perform(MockMvcRequestBuilders
				.put(uriString + "/5")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(dados)))
		.andExpect(MockMvcResultMatchers
				.status()
				.isOk());
	}
	
	@Test
	public void shouldDeleteById() throws Exception {
		 doNothing().when(service).deleteById(5L);;

        mockMvc.perform(MockMvcRequestBuilders
        		.delete(uriString + "/5"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
	}
}
