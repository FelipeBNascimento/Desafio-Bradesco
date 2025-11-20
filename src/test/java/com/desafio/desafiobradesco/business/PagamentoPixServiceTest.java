package com.desafio.desafiobradesco.business;

import com.desafio.desafiobradesco.infrastructure.dtos.Converter;
import com.desafio.desafiobradesco.infrastructure.dtos.PagamentoViaPixResponse;
import com.desafio.desafiobradesco.infrastructure.entity.PagamentoPixEntity;
import com.desafio.desafiobradesco.infrastructure.exceptions.ValorPositivo;
import com.desafio.desafiobradesco.infrastructure.repository.PagamentoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PagamentoPixServiceTest {


    @InjectMocks
    PagamentoPixService service;

    @Mock
    PagamentoRepository repository;

    @Mock
    Converter converter;


    private PagamentoPixEntity mockEntity(){

        PagamentoPixEntity pagamentoPixEntity = new PagamentoPixEntity();

        pagamentoPixEntity.setId(1L);
        pagamentoPixEntity.setNome("Felipe Bazan");
        pagamentoPixEntity.setValor(25.0);
        pagamentoPixEntity.setData(LocalDate.now());
        pagamentoPixEntity.setDescricao("teste Mockito");
        pagamentoPixEntity.setChave("123456789");
        pagamentoPixEntity.setCpf_cnpj("987654321-98");

        return pagamentoPixEntity;
    }


    private PagamentoViaPixResponse mockResponse(){

        PagamentoViaPixResponse pagamentoViaPixResponse = new PagamentoViaPixResponse();

        pagamentoViaPixResponse.setId(1L);
        pagamentoViaPixResponse.setNome("Felipe Bazan");
        pagamentoViaPixResponse.setValor(25.0);
        pagamentoViaPixResponse.setData(LocalDate.now());
        pagamentoViaPixResponse.setDescricao("teste Mockito");
        pagamentoViaPixResponse.setChave("123456789");
        pagamentoViaPixResponse.setCpf_cnpj("987654321-98");

        return pagamentoViaPixResponse;
    }

    @Test
    void deveSalvarComSucesso() {

        PagamentoPixEntity pagamentoPixEntity = mockEntity();

        when(repository.save(pagamentoPixEntity)).thenReturn(pagamentoPixEntity);
        PagamentoPixEntity pagamento = service.fazerTransferencia(pagamentoPixEntity);
        assertEquals(pagamento, pagamentoPixEntity);
        verify(repository).save(pagamentoPixEntity);

    }

    @Test
    void deveGerarumErroPorqueOvalorSeraNegativoParaTrasnferencia() {

        Double valor = -10.0;

        Assertions.assertThrows(ValorPositivo.class, () ->
                {
                    service.RegraPixValor(valor);
                }, "O valor é negaivo"
        );

    }

    @Test
    void naoDeveLancarExcessaoPoisSeraUmValorPositivo(){

        Double valor = 100.0;

        Assertions.assertDoesNotThrow(() ->{
            service.RegraPixValor(valor);

        }, "O teste falhou: Uma exceção inesperada foi lançada");
    }

    @Test
    void deveCalcularSomaEPorcentagensCorretamente(){

        String nome = "teste";
        LocalDate date = LocalDate.now();


        PagamentoPixEntity e1 = mockEntity();
        e1.setId(1L);
        e1.setValor(10.0);

        PagamentoPixEntity e2 = mockEntity();
        e2.setId(2L);
        e2.setValor(90.0);

        List<PagamentoPixEntity> lista = new ArrayList<>();
        lista.add(e1);
        lista.add(e2);


        PagamentoViaPixResponse r1 = mockResponse();
        r1.setId(1L);
        r1.setValor(10.0);

        PagamentoViaPixResponse r2 = mockResponse();
        r2.setId(2L);
        r2.setValor(30.0);

        when(repository.findAllByNomeAndData(nome, date)).thenReturn(lista);
        when(converter.paraResponse(e1)).thenReturn(r1);
        when(converter.paraResponse(e2)).thenReturn(r2);

        List<PagamentoViaPixResponse> retornoDoMetotoService = service.mostrarPagamentoDia(nome,date);

        Assertions.assertEquals(10.0, retornoDoMetotoService.get(0).getPorcentagens(), 0.0001);
        Assertions.assertEquals(90.0, retornoDoMetotoService.get(1).getPorcentagens(), 0.0001);








    }

}