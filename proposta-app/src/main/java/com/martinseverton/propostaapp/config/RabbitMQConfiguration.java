package com.martinseverton.propostaapp.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class RabbitMQConfiguration {

    //Exchanges
    @Value("${rabbitmq.propostapendente.exchange}")
    private String exchangePropostaPendente;

    @Value("${rabbitmq.propostaconcluida.exchange}")
    private String exchangePropostaConcluida;

    @Value("${rabbitmq.propostapendente.exchange.dlx}")
    private String exchangePropostaPendenteDlx;

    @Value("${rabbitmq.propostaconcluida.exchange.dlx}")
    private String exchangePropostaConcluidaDlx;

    //Filas
    @Value("${rabbitmq.queue.proposta.pendente.analise-credito}")
    private String filaPendenteMSAnaliseCredito;

    @Value("${rabbitmq.queue.proposta.pendente.notificacao}")
    private String filaPendenteMSNotificacao;

    @Value("${rabbitmq.queue.proposta.concluida.proposta-app}")
    private String filaConcluidaMSProposta;

    @Value("${rabbitmq.queue.proposta.concluida.notificacao}")
    private String filaConcluidaMSNotificacao;

    //Filas DLQs
    @Value("${rabbitmq.queue.proposta.pendente.analise-credito.dlq}")
    private String filaPendenteMSAnaliseCreditoDLQ;

    @Value("${rabbitmq.queue.proposta.pendente.notificacao.dlq}")
    private String filaPendenteMSNotificacaoDLQ;

    @Value("${rabbitmq.queue.proposta.concluida.proposta.dlq}")
    private String filaConcluidaMSPropostaDLQ;

    @Value("${rabbitmq.queue.proposta.concluida.notificacao.dlq}")
    private String filaConcluidaMSNotificacaoDLQ;

    @Bean
    public Queue criarFilaPendeteMsAnaliseCredito(){
        return QueueBuilder.durable(filaPendenteMSAnaliseCredito)
                .deadLetterExchange(exchangePropostaPendenteDlx)
                .build();
    }

    @Bean
    public Queue criarFilaPendeteMsNotificacao(){
        return QueueBuilder.durable(filaPendenteMSNotificacao)
                .deadLetterExchange(exchangePropostaPendenteDlx)
                .build();
    }

    @Bean
    public Queue criarFilaConcluidaMsProposta(){
        return QueueBuilder.durable(filaConcluidaMSProposta)
                .deadLetterExchange(exchangePropostaConcluidaDlx)
                .build();
    }

    @Bean
    public Queue criarFilaConcluidaMsNotificacao(){
        return QueueBuilder.durable(filaConcluidaMSNotificacao)
                .deadLetterExchange(exchangePropostaConcluidaDlx)
                .build();
    }

    @Bean
    public RabbitAdmin criarRabbitAdmin(ConnectionFactory connectionFactory){
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> inicializarAdmin(RabbitAdmin rabbitAdmin){
        return event -> rabbitAdmin.initialize();
    }

    @Bean
    public FanoutExchange criarFanoutExchangePropostaPendente(){
        return ExchangeBuilder.fanoutExchange(exchangePropostaPendente).build();
    }

    @Bean
    public FanoutExchange criarFanoutExchangePropostaConcluida(){
        return ExchangeBuilder.fanoutExchange(exchangePropostaConcluida).build();
    }

    @Bean
    public Binding criarBindingPropostaPendenteMSAnaliseCredito(){
        return BindingBuilder.bind(criarFilaPendeteMsAnaliseCredito())
                .to(criarFanoutExchangePropostaPendente());
    }

    @Bean
    public Binding criarBindingPropostaPendenteMSNotificacao(){
        return BindingBuilder.bind(criarFilaPendeteMsNotificacao())
                .to(criarFanoutExchangePropostaPendente());
    }

    @Bean
    public Binding criarBindingPropostaConcluidaMSPropostaApp(){
        return BindingBuilder.bind(criarFilaConcluidaMsProposta())
                .to(criarFanoutExchangePropostaConcluida());
    }

    @Bean
    public Binding criarBindingPropostaConcluidaMSNotificacao(){
        return BindingBuilder.bind(criarFilaConcluidaMsNotificacao())
                .to(criarFanoutExchangePropostaConcluida());
    }

    @Bean
    public Queue criarFilaPropostaPendenteMSAnaliseCreditoDLQ(){
        return QueueBuilder.durable(filaPendenteMSAnaliseCreditoDLQ).build();
    }

    @Bean
    public Queue criarFilaPropostaPendenteMSNotificacaoDLQ(){
        return QueueBuilder.durable(filaPendenteMSNotificacaoDLQ).build();
    }

    @Bean
    public FanoutExchange deadLetterExchangePropostaPendente(){
        return ExchangeBuilder.fanoutExchange(exchangePropostaPendenteDlx).build();
    }

    @Bean
    public Queue criarFilaPropostaConcluidaMSPropostaDLQ(){
        return QueueBuilder.durable(filaConcluidaMSPropostaDLQ).build();
    }

    @Bean
    public Queue criarFilaPropostaConcluidaMSNotificacaoDLQ(){
        return QueueBuilder.durable(filaConcluidaMSNotificacaoDLQ).build();
    }

    @Bean
    public FanoutExchange deadLetterExchangePropostaConcluida(){
        return ExchangeBuilder.fanoutExchange(exchangePropostaConcluidaDlx).build();
    }

    @Bean
    public Binding criarBindingPropostaPendenteMSAnaliseCreditoDLQ(){
        return BindingBuilder.bind(criarFilaPropostaPendenteMSAnaliseCreditoDLQ()).to(deadLetterExchangePropostaPendente());
    }

    @Bean
    public Binding criarBindingPropostaPendenteMSNotificacaoDLQ(){
        return BindingBuilder.bind(criarFilaPropostaPendenteMSNotificacaoDLQ()).to(deadLetterExchangePropostaPendente());
    }

    @Bean
    public Binding criarBindingPropostaConcluidaMSPropostaDLQ(){
        return BindingBuilder.bind(criarFilaPropostaConcluidaMSPropostaDLQ()).to(deadLetterExchangePropostaConcluida());
    }

    @Bean
    public Binding criarBindingPropostaConcluidaMSNotificacaoDLQ(){
        return BindingBuilder.bind(criarFilaPropostaConcluidaMSNotificacaoDLQ()).to(deadLetterExchangePropostaConcluida());
    }

    @Bean
    public MessageConverter jackson2JsonMessageConverter(){
        return new Jackson2JsonMessageConverter();

    }
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        return rabbitTemplate;
    }
}
