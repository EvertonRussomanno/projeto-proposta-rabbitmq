package com.martinseverton.analisecredito.service.strategy.impl;

import com.martinseverton.analisecredito.domain.Proposta;
import com.martinseverton.analisecredito.service.strategy.CalculoPonto;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class OutrosEmprestimosEmAndamentoImpl implements CalculoPonto {

    @Override
    public int calcular(Proposta proposta) {
        return outroEmprestimosEmAndamento() ? 0 : 80;
    }

    private boolean outroEmprestimosEmAndamento(){
        return new Random().nextBoolean();
    }
}
