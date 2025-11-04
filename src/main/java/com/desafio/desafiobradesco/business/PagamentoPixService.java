package com.desafio.desafiobradesco.business;

import com.desafio.desafiobradesco.infrastructure.dtos.Converter;
import com.desafio.desafiobradesco.infrastructure.dtos.PagamentoViaPixResponse;
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
    private final Converter converter;

    // criando metodo para fazer a transferancia
    public void fazerTransferencia(PagamentoPixEntity pagamentoPixEntity){

        // verificando a regra de negocio
        RegraPixValor(pagamentoPixEntity.getValor());
        // salvando o pagamento realizado no banco de dados
        pagamentoRepository.save(pagamentoPixEntity);

    }

    // adicionando uma regra de negocio que nao pode transferia valores 0 ou negativos
    public void RegraPixValor (Double valor){

        if (valor <= 0){
            throw new ValorPositivo("O valor abaixo de zero não pode ser transferido" + valor);
        }
    }

    public List<PagamentoViaPixResponse> mostrarPagamentoDia(String nome, LocalDate data){

        // Buscando a lista de pagamentos no banco de dado pelo nome e a data
        List<PagamentoPixEntity> listapagamento =  pagamentoRepository.findAllByNomeAndData(nome, data);

        // Calculando o montante de valor que foi feito
        Double soma = listapagamento.stream()
                .mapToDouble(PagamentoPixEntity::getValor)
                .sum();

        // Criando uma lista de response para retorno
        List<PagamentoViaPixResponse> pagamentosComPorcentagem = new ArrayList<>();

        // usando for para fazer a iteração e calcular a porcentagem por valor
        for (PagamentoPixEntity pagameto: listapagamento){

            // Criando um objeto response
            PagamentoViaPixResponse response = converter.paraResponse(pagameto);

            // Calculo da porcentagem
            Double porcentagem = ((pagameto.getValor() / soma)*100);

            // setando a porcentagem calculada
            response.setPorcentagens(porcentagem);

            // adicionando um objeto response na lista criada
            pagamentosComPorcentagem.add(response);

        }

        // retornando a lista
        return pagamentosComPorcentagem;

    }

}
