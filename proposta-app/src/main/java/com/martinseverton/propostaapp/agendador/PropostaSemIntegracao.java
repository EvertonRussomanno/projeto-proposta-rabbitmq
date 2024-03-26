package com.martinseverton.propostaapp.agendador;

import com.martinseverton.propostaapp.repository.PropostaRepository;
import com.martinseverton.propostaapp.service.NotificacaoRabbitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class PropostaSemIntegracao {

    private final PropostaRepository propostaRepository;

    private final NotificacaoRabbitService notificacaoRabbitService;

    private final String exchange;

    private final Logger logger = LoggerFactory.getLogger(PropostaSemIntegracao.class);

    public PropostaSemIntegracao(PropostaRepository propostaRepository,
                                 NotificacaoRabbitService notificacaoRabbitService,
                                 @Value("${rabbitmq.propostapendente.exchange}") String exchange) {
        this.propostaRepository = propostaRepository;
        this.notificacaoRabbitService = notificacaoRabbitService;
        this.exchange = exchange;
    }

    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    public void buscarPropostasSemIntegracao(){
        propostaRepository.findAllByIntegradaIsFalse().forEach(proposta -> {
            try{
                notificacaoRabbitService.notificar(proposta, exchange);
                proposta.setIntegrada(true);
                propostaRepository.save(proposta);
            }catch (RuntimeException ex){
                logger.error(ex.getMessage());
            }
        });
    }
}
