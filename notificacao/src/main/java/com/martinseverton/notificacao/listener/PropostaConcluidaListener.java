package com.martinseverton.notificacao.listener;

import com.martinseverton.notificacao.constante.MensagemConstante;
import com.martinseverton.notificacao.domain.Proposta;
import com.martinseverton.notificacao.service.NotificacaoSnsService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PropostaConcluidaListener {

    @Autowired
    private NotificacaoSnsService notificacaoSnsService;

    @RabbitListener(queues = "${rabbitmq.queue.proposta.concluida}")
    public void propostaPendente(Proposta proposta){
        if(proposta.getAprovada()){
            String mensagem = String.format(MensagemConstante.PROPOSTA_APROVADA, proposta.getUsuario().getNome());
            notificacaoSnsService.notificar(proposta.getUsuario().getTelefone(), mensagem);
        }else{
            String mensagem = proposta.getObservacao().contains("negativado") ? String.format(MensagemConstante.PROPOSTA_REJEITADA_NEGATIVADO, proposta.getUsuario().getNome())
                    : String.format(MensagemConstante.PROPOSTA_EM_SCORE_BAIXO, proposta.getUsuario().getNome());
            notificacaoSnsService.notificar(proposta.getUsuario().getTelefone(), mensagem);
        }
    }
}
