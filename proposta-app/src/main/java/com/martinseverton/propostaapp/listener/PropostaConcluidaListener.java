package com.martinseverton.propostaapp.listener;

import com.martinseverton.propostaapp.entity.Proposta;
import com.martinseverton.propostaapp.mapper.PropostaMapper;
import com.martinseverton.propostaapp.repository.PropostaRepository;
import com.martinseverton.propostaapp.service.WebSocketService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PropostaConcluidaListener {

    private final PropostaRepository propostaRepository;

    private final WebSocketService webSocketService;

    public PropostaConcluidaListener(PropostaRepository propostaRepository, WebSocketService webSocketService) {
        this.propostaRepository = propostaRepository;
        this.webSocketService = webSocketService;
    }

    @RabbitListener(queues = "${rabbitmq.queue.proposta.concluida.proposta-app}")
    public void propostaConcluida(Proposta proposta){
        atualizarProposta(proposta);
        webSocketService.notificar(PropostaMapper.INSTANCE.convertEntityToDto(proposta));
    }

    private void atualizarProposta(Proposta proposta) {
        propostaRepository.atualizarProposta(proposta.getId(), proposta.getAprovada(), proposta.getObservacao());
    }
}
