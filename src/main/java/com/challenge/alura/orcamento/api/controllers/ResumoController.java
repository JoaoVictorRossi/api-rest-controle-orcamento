package com.challenge.alura.orcamento.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.alura.orcamento.api.dto.resumo.DadosResumo;
import com.challenge.alura.orcamento.api.services.DespesaService;
import com.challenge.alura.orcamento.api.services.ReceitaService;
import com.challenge.alura.orcamento.api.services.ResumoService;

@RestController
@RequestMapping("/resumo")
public class ResumoController {
	
	@Autowired
	private ResumoService resumoService;
	
	@Autowired
	private ReceitaService receitaService;
	
	@Autowired
	private DespesaService despesaService;
	
	@GetMapping(value = "/{ano}/{mes}")
	public ResponseEntity<DadosResumo> listarResumoMes(@PathVariable Integer ano, @PathVariable Integer mes){
		DadosResumo resumo = resumoService.getResumo(
				resumoService.calculateBalanceReceita(receitaService.findAllByTempo(ano, mes).toList()), 
				resumoService.calculateBalanceDespesa(despesaService.findAllByTempo(ano, mes).toList()),
				resumoService.getValuePerCategorie(mes, ano));
		return ResponseEntity.ok(resumo);
	}
}


