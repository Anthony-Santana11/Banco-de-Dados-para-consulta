# Projeto Consulta

Sistema de gerenciamento de consultas desenvolvido com Spring Boot.

## Tecnologias

Este projeto foi desenvolvido com as seguintes tecnologias:

- Java 24
- Spring Boot 3.4.5
- PostgreSQL
- JPA / Hibernate
- Maven
- Lombok

## Pré-requisitos

Antes de começar, verifique se você tem os seguintes requisitos:

- Java Development Kit (JDK) 24
- Maven
- PostgreSQL
- IDE de sua preferência (recomendado: IntelliJ IDEA ou Eclipse)

## Instalação e Configuração

1. Clone o repositório:
```bash
git clone [url-do-seu-repositorio]
```

2. Entre no diretório do projeto:
```bash
cd projeto_consulta
```

3. Compile o projeto:
```bash
mvn clean install
```

4. Configure o banco de dados PostgreSQL no arquivo `application.properties`

5. Execute o projeto:
```bash
mvn spring-boot:run
```

## Funcionalidades

- Gerenciamento de consultas médicas
- Cadastro de pacientes
- Cadastro de médicos
- Agendamento de consultas
- Validações de regras de negócio

## Arquitetura do Projeto

O projeto segue a arquitetura em camadas:

- Controllers: Endpoints da API
- Services: Regras de negócio
- Repositories: Acesso ao banco de dados
- Entities: Modelos de dados
- DTOs: Objetos de transferência de dados

## Licença

Este projeto está sob a licença [NOVAGM] - veja o arquivo LICENSE.md para detalhes.

## Autor

* **Anthony Santana** - *Desenvolvimento* - [Anthony-Santana11](https://github.com/Anthony-Santana11)

## Versão

Versão atual: 0.0.1-SNAPSHOT
