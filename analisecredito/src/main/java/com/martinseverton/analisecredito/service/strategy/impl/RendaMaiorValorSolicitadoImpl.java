package com.martinseverton.analisecredito.service.strategy.impl;

import com.martinseverton.analisecredito.domain.Proposta;
import com.martinseverton.analisecredito.service.strategy.CalculoPonto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class RendaMaiorValorSolicitadoImpl implements CalculoPonto {

    @Override
    public int calcular(Proposta proposta) {
        return rendaMaiorValorSolicitado(proposta) > 0 ? 100 : 0;
    }

    private int rendaMaiorValorSolicitado(Proposta proposta){
        BigDecimal renda = proposta.getUsuario().getRenda();
        return renda.compareTo(proposta.getValorSolicitado());
    }
}
