# Desafio Bradesco: API de Processamento de Pagamentos PIX

Este projeto é uma solução backend desenvolvida em Spring Boot para processar transações PIX, persistir os dados e fornecer um extrato diário detalhado, incluindo a porcentagem que cada pagamento representa do montante total transferido na data.

## Tecnologias Utilizadas

Linguagem: Java 17+

Framework: Spring Boot 3

Banco de Dados: PostgreSQL (Banco de dados relacional robusto)

ORM: Spring Data JPA

Ferramenta: Maven

Outros: Lombok (para simplificar classes Entity e DTO)

## Estrutura da Aplicação

Camada

Função

### Controller

Recebe as requisições HTTP (POST e GET), valida parâmetros e chama a camada de Service.

### Service (Business)

Contém a lógica de negócio principal: validação de valor (RegraPixValor), cálculo do montante total e cálculo da porcentagem individual.

### Repository (Data)

Interface Spring Data JPA para comunicação com o banco de dados.

### Entity

Mapeamento da tabela pagamento no banco de dados.

### DTOs

Objetos de Transferência de Dados para formatar a resposta da API de Extrato.

## Como Rodar o Projeto

Clone o Repositório:

git clone https://github.com/FelipeBNascimento/Desafio-Bradesco.git
cd [pasta-que deseje colar]

Observação para PostgreSQL: Certifique-se de que as configurações de conexão (URL, usuário e senha) no seu arquivo application.properties ou application.yml estão corretas e que o serviço do PostgreSQL está ativo.


### Executar a Aplicação:


A API estará disponível em http://localhost:8080.

Endpoints da API (Testes com Postman)

A API possui dois endpoints principais sob o mapeamento /pagamentos.

1. Criar um Novo Pagamento PIX

Endpoint para registrar uma nova transação.

Verbo: POST

URL: http://localhost:8080/pagamentos

Header: Content-Type: application/json

Exemplo de Body:

{
"nome": "Carla Oliveira",
"valor": 150.00,
"data": "2025-11-04",
"descricao": "Reembolso de despesas",
"chave": "carla.pix@empresa.com",
"cpf_cnpj": "111.222.333-44"
}


Retorno Esperado:

Status 200 OK: Sucesso na persistência.

Status 500/400: Falha na validação (valor <= 0).

2. Listar Extrato com Porcentagens

Endpoint para consultar todos os pagamentos feitos por uma pessoa em uma data, retornando a porcentagem de cada pagamento em relação ao total do dia.

Verbo: GET

URL: http://localhost:8080/pagamentos/{pessoa}?date={data}

Parâmetros de Caminho e Query:

{pessoa} (Path Variable): O nome da pessoa para a qual o pagamento foi feito. (Ex: Carla Oliveira)

date (Query Parameter): A data no formato YYYY-MM-DD. (Ex: 2025-11-04)

Exemplo de URL:

http://localhost:8080/pagamentos/Carla Oliveira?date=2025-11-04



### Retorno Esperado:
Uma lista de DTOs, onde cada objeto contém os dados do pagamento e a sua porcentagem individual.

[
{
"nome": "Carla Oliveira",
"valor": 150.00,
"data": "2025-11-04",
"descricao": "Reembolso de despesas",
"chave": "carla.pix@empresa.com",
"cpf_cnpj": "111.222.333-44",
"porcentagemDoDia": 75.00  // Exemplo: 150 / 200 = 75%
},
{
"nome": "Carla Oliveira",
"valor": 50.00,
"data": "2025-11-04",
"descricao": "Transferência pessoal",
"chave": "5511998765432",
"cpf_cnpj": "111.222.333-44",
"porcentagemDoDia": 25.00 // Exemplo: 50 / 200 = 25%
}
]
