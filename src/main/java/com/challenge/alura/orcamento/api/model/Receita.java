package com.challenge.alura.orcamento.api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.Date;

import com.challenge.alura.orcamento.api.records.DadosReceita;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Receita")
@Table(name = "tb_receitas")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Receita implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String descricao;
	private BigDecimal valor;
	private Date tempo;
	
	public Receita(DadosReceita dados) {
		this.descricao = dados.descricao();
		this.valor = dados.valor();
		this.tempo = Date.from(dados.tempo().atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

}
