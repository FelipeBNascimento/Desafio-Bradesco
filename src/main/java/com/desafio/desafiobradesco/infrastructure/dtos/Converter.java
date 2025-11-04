package com.desafio.desafiobradesco.infrastructure.dtos;

import com.desafio.desafiobradesco.infrastructure.entity.PagamentoPixEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class Converter {

    public PagamentoViaPixResponse paraResponse(PagamentoPixEntity pagamentoPixEntity) {


        PagamentoViaPixResponse pagamentoViaPixResponse = PagamentoViaPixResponse.builder()

                .nome(pagamentoPixEntity.getNome())
                .data(pagamentoPixEntity.getData())
                .valor(pagamentoPixEntity.getValor())
                .descricao(pagamentoPixEntity.getDescricao())
                .cpf_cnpj(pagamentoPixEntity.getCpf_cnpj())
                .chave(pagamentoPixEntity.getChave())

                .build();
        return pagamentoViaPixResponse;

    }

}

