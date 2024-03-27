 # Estudos de microserviços e mensageria.
 ## Conjunto de três microserviços que compõem uma aplicação back-end para simulação de um cadastro de proposta, envio de notificação e análise de crédito.

 <br/>

## Tecnologias utilizadas:
### Java
### Spring Boot
### Spring Data JPA
### Mapstruct
### RabbitMQ
### Postgres
### Docker
### Postman
### Amazon AWS SNS

<br/>

## 1° microserviço -> proposta-app
### Responsável por conversar com o front-end, banco de dados, criação e escuta de filas, exchanges e binds.

## 2° microserviço -> analisecredito
### Responsável por escutar a fila de proposta pendente e realizar as regras de negócio referente a analise de crédito.

## 3° microserviço -> notificacao
### Responsável por escutar as filas de proposta pendente e proposta concluida para envio de notificações.

<br/>

## Necessário ter uma conta na AWS com um usuário cadastrado e com as credenciais que deverá ser configurada no applicatio.properties do microserviço notificacao.
## Necessário ter um número de telefone válido devidamente cadastrado no serviço AWS SNS e verificado.

<br/>

## Para rodar tenha o docker instalado e execute os seguintes comandos:
## Front-End -> http://localhost
### docker run -d -p 80:80 --name proposta-web-container matheuspieropan/proposta-web
## Postgres
### docker run --name postgres-container -d -e POSTGRES_PASSWORD=123 -e POSTGRES_DB=propostadb -p 5433:5432 postgres
## RabbitMQ -> http://localhost:15672
### docker run -d -p 5672:5672 -p 15672:15672 --name my-rabbit rabbitmq:3-management

<br/>

## Em seguida rode os três microserviços em sua IDE de preferência.

<br/>

## Endpoints:
### http://localhost:8080/proposta -> POST
#### Body exemple:
```JSON
{
  "nome": "Everton",
  "sobrenome": "Alfredo",
  "telefone": "5515996647913",
  "cpf": "123.456.789-01",
  "renda": 5780.00,
  "valorSolicitado": 1700.00,
  "prazoPagamento": 12
}
```

<br/>

### http://localhost:8080/proposta -> GET
#### Para consultar propostas cadastradas no banco de dados
