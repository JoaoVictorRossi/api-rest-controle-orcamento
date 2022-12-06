package com.challenge.alura.orcamento.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.challenge.alura.orcamento.api.model.Receita;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {
}
