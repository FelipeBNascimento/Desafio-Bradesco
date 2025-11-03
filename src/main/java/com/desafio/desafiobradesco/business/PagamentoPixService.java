package com.desafio.desafiobradesco.business;

import com.desafio.desafiobradesco.infrastructure.entity.PagamentoPixEntity;
import com.desafio.desafiobradesco.infrastructure.exceptions.ValorPositivo;
import com.desafio.desafiobradesco.infrastructure.repository.PagamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PagamentoPixService {

    private final PagamentoRepository pagamentoRepository;

    public void fazerTransferencia(PagamentoPixEntity pagamentoPixEntity){

        RegraPixValor(pagamentoPixEntity.getValor());
        pagamentoRepository.save(pagamentoPixEntity);

    }

    public void RegraPixValor (Double valor){

        if (valor < 0){
            throw new ValorPositivo(    "O valor abaixo de zero não pode ser transferido" + valor);
        }
    }

    public List<PagamentoPixEntity> mostrarPagamentoDia(String nome, LocalDate data){

        return pagamentoRepository.findAllByNomeAndData(nome, data);
    }

    public List<Double> Porcentagem(String nome, LocalDate data){

        List<PagamentoPixEntity> listapagamento = pagamentoRepository.findAllByNomeAndData
                (nome, data);

        Double soma = listapagamento.stream()
                .mapToDouble(PagamentoPixEntity::getValor)
                .sum();

        List<Double> porcentagens = new ArrayList<>();

        for (PagamentoPixEntity pagameto: listapagamento){

             porcentagens.add((pagameto.getValor() / soma)*100);

        }

        return porcentagens;

    }


}
