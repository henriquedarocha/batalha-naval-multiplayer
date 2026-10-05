# Batalha Naval Multiplayer

Um jogo clássico de Batalha Naval online com suporte para partidas multiplayer em tempo real. Este projeto está sendo desenvolvido para compor o meu portfólio full-stack, integrando uma arquitetura back-end robusta em Java e Spring Boot com uma interface em React, com o objetivo principal de aplicar na prática o gerenciamento de estado no front-end e a comunicação bidirecional via WebSockets.

## Status

Em desenvolvimento inicial. Atualmente, a estrutura base do projeto e a modelagem estrutural do banco de dados relacional estão sendo definidas.

## Tecnologias

- Java 17 e Spring Boot 4
- PostgreSQL e Flyway
- Docker
- React (em breve)
- WebSockets com STOMP (em breve)

## Como executar

Pré-requisitos: Java 17, Docker e Git.

```bash
git clone https://github.com/henriquedarocha/batalha-naval-multiplayer.git
cd batalha-naval-multiplayer
cp .env.example .env
docker compose up -d
cd Back-End
./mvnw spring-boot:run
```

## Próximas etapas

- Cadastro de jogadores
- Autenticação com JWT
- Interface em React
- Partidas em tempo real