package com.challenge.alura.orcamento.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.challenge.alura.orcamento.api.model.Receita;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {
	
	@Query("from Receita r where month(r.tempo) = :mes and year(r.tempo) = :ano and r.descricao = :descricaoDados")
	Receita findByTempoMes(@Param("mes") int mes, @Param("ano") int ano, @Param("descricaoDados") String descricaoDados);

	List<Receita> findByDescricao(String descricao);
	
	
	
}
