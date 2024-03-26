package com.martinseverton.analisecredito.service;

import com.martinseverton.analisecredito.domain.Proposta;
import com.martinseverton.analisecredito.exceptions.StrategyException;
import com.martinseverton.analisecredito.service.strategy.CalculoPonto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnaliseCreditoService {

    @Autowired
    private List<CalculoPonto> calculoPontoList;

    @Autowired
    private NotificacaoRabbitMQService notificacaoRabbitMQService;

    @Value("${rabbitmq.propostaconcluida.exchange}")
    private String exchangePropostaConcuida;

    public void analizar(Proposta proposta){
        try {
            boolean aprovada = calculoPontoList.stream()
                    .mapToInt(impl -> impl.calcular(proposta)).sum() > 350;
            proposta.setAprovada(aprovada);
        }catch (StrategyException ex){
            proposta.setAprovada(false);
            
        }
        notificacaoRabbitMQService.notificar(exchangePropostaConcuida, proposta);
    }
}
