package com.desafio.desafiobradesco.infrastructure.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "pagamento")
public class PagamentoPixEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "valor", nullable = false)
    private Double valor;

    @Column (name = "data", nullable = false)
    private LocalDate data;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "chave", nullable = false)
    private String chave;

    @Column(name = "cpf-cnpj", nullable = false)
    private String cpf_cnpj;


}
