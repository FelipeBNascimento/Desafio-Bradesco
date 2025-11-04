package com.desafio.desafiobradesco.infrastructure.dtos;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PagamentoViaPixResponse {

    private String nome;
    private Double valor;
    private LocalDate data;
    private String descricao;
    private String chave;
    private String cpf_cnpj;
    private Double porcentagens;



}
