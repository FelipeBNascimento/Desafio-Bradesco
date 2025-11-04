package com.desafio.desafiobradesco.infrastructure.repository;

import com.desafio.desafiobradesco.infrastructure.entity.PagamentoPixEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PagamentoRepository extends JpaRepository<PagamentoPixEntity, Long> {


    List<PagamentoPixEntity> findAllByNomeAndData(String nome, LocalDate date);
}
