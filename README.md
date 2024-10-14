# Otimização do Sistema de Parquímetro

Este projeto refatora um sistema de parquímetro, com foco na melhoria da otimização, persistência de dados e escalabilidade. Utilizando tecnologias modernas, como Java, Spring Boot, Docker, Swagger e MongoDB, o objetivo foi criar um sistema moderno e eficiente que passará a lidar com um grande volume de dados.


## Tecnologias Utilizadas

- **Java 17**: Versão de linguagem utilizada.
- **Spring Boot**: Framework para desenvolvimento de aplicações Java.
- **Spring Data MongoDB**: Para interação com o banco de dados.
- **Swagger**: Para documentação e testes das APIs.
- **MongoDB Atlas**: Plataforma de banco de dados MongoDB gerenciada na nuvem, que facilita a criação, escalabilidade e segurança de bancos de dados.

## Instruções para Executar a Aplicação

Para rodar a aplicação, siga os comandos abaixo:

1. **Faça login no Docker:**
   ```bash
   docker login

2. **Baixe a imagem mais recente no Docker Hub:**
     ```bash
    docker pull romulosousa865/parquimetro_app:latest

3. **Crie e inicie um conatiner a partir da imagem com o seguinte comando**
     ```bash
    docker run -p 8080:8080 -e MONGODB_USERNAME=parquimetro -e MONGODB_PASSWORD=passParquimetro -d romulosousa865/parquimetro_app:latest

## Entendendo o comando:
    
**docker run** inicia um novo container a partir de uma imagem especificada.

**-p 8080:8080**  mapeia a porta 8080 do host (computador local) para a porta 8080 do container, permitindo o acesso ao serviço que roda no container através da porta 8080 do host.

**-e MONGODB_USERNAME** e **-e MONGODB_PASSWORD**  Define variáveis de ambiente no container.

**-d** Executa o container em segundo plano, sem ocupar o terminal.

**romulosousa865/parquimetro_app:latest** a imagem que será usada para criar o container.

## Documentação da API

A documentação da API é gerada automaticamente pelo Swagger. Você pode acessá-la no seguinte endereço após iniciar a aplicação:

URL: [[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)]

Consulte a documentação do Swagger UI para ver todos os endpoints disponíveis e detalhes sobre cada um deles.

## Banco de Dados

A aplicação se conecta a um banco de dados hospedado na plataforma MongoDB Atlas, usando uma URL de conexão fornecida pelo serviço. As permissões de acesso e as credenciais são gerenciadas diretamente no Atlas, garantindo uma comunicação segura e permitindo que os dados sejam armazenados e manipulados de forma escalável na nuvem.
