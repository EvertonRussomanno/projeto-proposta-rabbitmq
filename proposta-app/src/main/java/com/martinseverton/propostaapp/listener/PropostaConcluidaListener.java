package com.martinseverton.propostaapp.listener;

import com.martinseverton.propostaapp.dto.PropostaResponseDTO;
import com.martinseverton.propostaapp.entity.Proposta;
import com.martinseverton.propostaapp.mapper.PropostaMapper;
import com.martinseverton.propostaapp.repository.PropostaRepository;
import com.martinseverton.propostaapp.service.WebSocketService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class PropostaConcluidaListener {

    private PropostaRepository propostaRepository;

    private WebSocketService webSocketService;

    @RabbitListener(queues = "${rabbitmq.queue.proposta.concluida}")
    public void propostaConcluida(Proposta proposta){
        propostaRepository.save(proposta);
        PropostaResponseDTO propostaResponseDTO = PropostaMapper.INSTANCE.convertEntityToDto(proposta);
        webSocketService.notificar(propostaResponseDTO);
    }
}
