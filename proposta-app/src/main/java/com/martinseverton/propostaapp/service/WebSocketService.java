package com.martinseverton.propostaapp.service;

import com.martinseverton.propostaapp.dto.PropostaResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public void notificar(PropostaResponseDTO proposta){
        simpMessagingTemplate.convertAndSend("/propostas", proposta);
    }
}
