package com.challenge.alura.orcamento.api.model;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.Date;

import com.challenge.alura.orcamento.api.dto.Dados;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Despesa")
@Table(name = "tb_despesas")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Despesa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String descricao;
	private BigDecimal valor;
	private Date tempo;
	
	public Despesa(Dados dados) {
		this.descricao = dados.getDescricao();
		this.valor = dados.getValor();
		this.tempo = Date.from(dados.getTempo().atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

}
