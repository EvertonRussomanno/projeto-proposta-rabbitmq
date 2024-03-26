package com.martinseverton.analisecredito.service.strategy;

import com.martinseverton.analisecredito.domain.Proposta;

public interface CalculoPonto {

    int calcular(Proposta proposta);
}
