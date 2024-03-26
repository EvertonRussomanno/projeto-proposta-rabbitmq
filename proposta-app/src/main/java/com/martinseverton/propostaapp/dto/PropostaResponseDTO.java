package com.martinseverton.propostaapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PropostaResponseDTO {

    private Long id;

    private String nome;

    private String sobrenome;

    private String cpf;

    private String telefone;

    private BigDecimal renda;

    private String valorSolicitadoFmt;

    private int prazoPagamento;

    private Boolean aprovada;

    private String observacao;
}
