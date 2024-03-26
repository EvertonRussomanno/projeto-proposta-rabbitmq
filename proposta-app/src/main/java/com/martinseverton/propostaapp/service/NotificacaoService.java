package com.martinseverton.propostaapp.service;

import com.martinseverton.propostaapp.dto.PropostaResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class NotificacaoService {

    private RabbitTemplate rabbitTemplate;

    public void notificar(PropostaResponseDTO proposta, String exchange){
        rabbitTemplate.convertAndSend(exchange, "", proposta);
    }
}
