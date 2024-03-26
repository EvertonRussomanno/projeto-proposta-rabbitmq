package com.martinseverton.propostaapp.service;

import com.martinseverton.propostaapp.dto.PropostaRequestDTO;
import com.martinseverton.propostaapp.dto.PropostaResponseDTO;
import com.martinseverton.propostaapp.entity.Proposta;
import com.martinseverton.propostaapp.repository.PropostaRepository;
import com.martinseverton.propostaapp.mapper.PropostaMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PropostaService {

    private PropostaRepository propostaRepository;

    private NotificacaoService notificacaoService;

    public PropostaResponseDTO criar(PropostaRequestDTO requestDTO){
        Proposta proposta = PropostaMapper.INSTANCE.convertDtoToProposta(requestDTO);
        propostaRepository.save(proposta);
        PropostaResponseDTO response = PropostaMapper.INSTANCE.convertEntityToDto(proposta);
        notificacaoService.notificar(response, "proposta-pendente.ex");
        return response;
    }

    public List<PropostaResponseDTO> obterProposta() {
        Iterable<Proposta> propostas = propostaRepository.findAll();
        return PropostaMapper.INSTANCE.convertListEntityToListDto(propostas);
    }
}
