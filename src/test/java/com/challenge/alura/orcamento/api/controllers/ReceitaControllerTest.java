package com.challenge.alura.orcamento.api.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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

import com.challenge.alura.orcamento.api.dto.receita.DadosAtualizacaoReceita;
import com.challenge.alura.orcamento.api.dto.receita.DadosCriacaoReceita;
import com.challenge.alura.orcamento.api.model.Receita;
import com.challenge.alura.orcamento.api.services.ReceitaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class ReceitaControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;
	
	@MockBean
	private ReceitaService service;

	private final String uriString = "/receitas";

	private List<DadosCriacaoReceita> ReceitasDTO;
	private List<Receita> Receitas;

	@BeforeEach
	public void setup() {

		this.ReceitasDTO = List.of(

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
						LocalDate.of(2022, 10, 05))

		);

		this.Receitas = List.of(
				new Receita(this.ReceitasDTO.get(0)), 
				new Receita(this.ReceitasDTO.get(1)),
				new Receita(this.ReceitasDTO.get(2)));
	}

	@Test
	public void shouldPostNewReceita() throws JsonProcessingException, Exception {
		DadosCriacaoReceita ReceitaDTO = this.ReceitasDTO.get(2);
		
		when(service.save(any())).thenReturn(this.Receitas.get(2));

		mockMvc.perform(MockMvcRequestBuilders.post(uriString).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(ReceitaDTO)))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}
	
	@Test
	public void whenPostWithAnyParameterIsNull_thenBadRequestAndReturnValidation() throws JsonProcessingException, Exception {
		DadosCriacaoReceita ReceitaDTO = new DadosCriacaoReceita(
				"Test", null, LocalDate.now());
		
		when(service.save(any())).thenReturn(this.Receitas.get(2));
		
		mockMvc.perform(MockMvcRequestBuilders
				.post(uriString)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(ReceitaDTO)))
		.andExpect(MockMvcResultMatchers
				.status().isBadRequest());
	}

	@Test
	public void shouldReturnAllReceitasByDescription() throws Exception {
		
		List<Receita> ReceitasFiltered = this.Receitas.stream()
				.filter(Receita -> Receita.getDescricao().toLowerCase().contains("workout"))
				.toList();
		
		when(service.findByDescricao("workout")).thenReturn(new PageImpl<>(ReceitasFiltered));
		
		mockMvc.perform(MockMvcRequestBuilders.get(uriString + "?descricao=workout"))
				.andExpect(MockMvcResultMatchers.status().isOk());

	}
	
	@Test
	public void shouldReturnAllReceitas() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(uriString))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void shouldReturnReceitaById() throws Exception {
		
		when(service.findById(5L)).thenReturn(Receitas.get(0));
		
		mockMvc.perform(MockMvcRequestBuilders
				.get(uriString + "/5")).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void shouldReturnListReceitasByDate() throws Exception {
		List<Receita> filteredList = Receitas.stream()
				.filter(Receitas -> Receitas.getTempo().getMonthValue() == 10 &&
				Receitas.getTempo().getYear() == 2022).toList();
		
		when(service.findAllByTempo(anyInt(), anyInt())).thenReturn(new PageImpl<>(filteredList));
		
		mockMvc.perform(MockMvcRequestBuilders
				.get(uriString + "/2022/10"))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void shouldUpdateReceitaById() throws JsonProcessingException, Exception {
		
		
		
		DadosAtualizacaoReceita dados = new DadosAtualizacaoReceita("Test", null, null);
		
		Receita Receita = Receitas.get(0);
		
		when(service.update(dados, 5L)).thenReturn(Receita);
		
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
