package com.martinseverton.propostaapp.service;

import com.martinseverton.propostaapp.dto.PropostaRequestDTO;
import com.martinseverton.propostaapp.dto.PropostaResponseDTO;
import com.martinseverton.propostaapp.entity.Proposta;
import com.martinseverton.propostaapp.mapper.PropostaMapper;
import com.martinseverton.propostaapp.repository.PropostaRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropostaService {

    private final PropostaRepository propostaRepository;

    private final NotificacaoRabbitService notificacaoRabbitService;

    @Value("${rabbitmq.propostapendente.exchange}")
    private final String exchange;

    public PropostaService(PropostaRepository propostaRepository,
                           NotificacaoRabbitService notificacaoRabbitService,
                           @Value("${rabbitmq.propostapendente.exchange}") String exchange) {
        this.propostaRepository = propostaRepository;
        this.notificacaoRabbitService = notificacaoRabbitService;
        this.exchange = exchange;
    }

    public PropostaResponseDTO criar(PropostaRequestDTO requestDTO){
        Proposta proposta = PropostaMapper.INSTANCE.convertDtoToProposta(requestDTO);
        propostaRepository.save(proposta);

        notificarRabbitMQ(proposta);

        return PropostaMapper.INSTANCE.convertEntityToDto(proposta);
    }

    public void notificarRabbitMQ(Proposta proposta){
        try {
            notificacaoRabbitService.notificar(proposta, exchange);

        }catch (RuntimeException ex){
            proposta.setIntegrada(false);
            propostaRepository.save(proposta);
        }
    }


    public List<PropostaResponseDTO> obterProposta() {
        Iterable<Proposta> propostas = propostaRepository.findAll();
        return PropostaMapper.INSTANCE.convertListEntityToListDto(propostas);
    }
}
