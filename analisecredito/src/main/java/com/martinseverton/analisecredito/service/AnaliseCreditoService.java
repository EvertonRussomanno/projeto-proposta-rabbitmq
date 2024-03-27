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

    public void analisar(Proposta proposta){
        // forçar erro para teste dlq
        //proposta.getObservacao().toLowerCase();
        try {
            int pontos = calculoPontoList.stream()
                    .mapToInt(impl -> impl.calcular(proposta)).sum();
            proposta.setAprovada(pontos > 350);
        }catch (StrategyException ex){
            proposta.setAprovada(false);
            proposta.setObservacao(ex.getMessage());
        }
        notificacaoRabbitMQService.notificar(exchangePropostaConcuida, proposta);
    }
}
