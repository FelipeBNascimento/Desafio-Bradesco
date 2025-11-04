package com.desafio.desafiobradesco.controller;

import com.desafio.desafiobradesco.business.PagamentoPixService;
import com.desafio.desafiobradesco.infrastructure.dtos.PagamentoViaPixResponse;
import com.desafio.desafiobradesco.infrastructure.entity.PagamentoPixEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/pagamentos")
@RequiredArgsConstructor
public class PagamentoViaPixController {

    private final PagamentoPixService service;

    // metodo POST para tansferencia
    @PostMapping
    public ResponseEntity<Void> transferir(@RequestBody PagamentoPixEntity pagamentoPixEntity){

        service.fazerTransferencia(pagamentoPixEntity);

        return ResponseEntity.ok().build();
    }

    // Metodo GET para visualização de pagamento por pessoa e a data
    @GetMapping("{pessoa}")
    public ResponseEntity<List<PagamentoViaPixResponse>> listar(@PathVariable String pessoa,
                                                                @RequestParam LocalDate date){

        return ResponseEntity.ok(service.mostrarPagamentoDia(pessoa,date));
    }



}
