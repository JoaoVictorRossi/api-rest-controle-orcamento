package com.challenge.alura.orcamento.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.challenge.alura.orcamento.api.model.Despesa;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long>{

	@Query("from Despesa d where month(d.tempo) = :mes and year(d.tempo) = :ano and d.descricao = :descricaoDados")
	Despesa findByTempoMes(@Param("mes") int mes, @Param("ano") int ano, @Param("descricaoDados") String descricaoDados);
	
	List<Despesa> findByDescricao(String descricao);
}
