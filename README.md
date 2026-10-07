# Batalha Naval Multiplayer

Jogo de Batalha Naval online para dois jogadores, com partidas em tempo real. O projeto faz parte do meu portfólio full-stack e tem dois objetivos de estudo: construir uma API em Java com Spring Boot seguindo boas práticas de arquitetura e segurança, e aprender React e comunicação em tempo real com WebSockets.

## Status

Em desenvolvimento. A primeira fase do back-end está concluída:

- Cadastro de jogadores, com validação dos dados e senha armazenada como hash BCrypt
- Login com emissão de token JWT
- Rota protegida que identifica o jogador pelo token
- Respostas de erro padronizadas

A próxima fase é a interface em React.

## Tecnologias

- Java 17 e Spring Boot 4
- Spring Security e JWT
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

Antes de subir a aplicação, abra o arquivo `.env` e substitua os valores de exemplo, principalmente o `JWT_SECRET`, que deve ser um texto aleatório com pelo menos 32 caracteres.

A API fica disponível em `http://localhost:8080`. O banco de dados roda em um container Docker, na porta 5433.

## API

| Método | Rota | Autenticação | Descrição |
|---|---|---|---|
| POST | `/api/players` | Não | Cadastra um jogador |
| POST | `/api/auth/login` | Não | Valida as credenciais e devolve um token JWT |
| GET | `/api/players/me` | Bearer token | Devolve os dados do jogador autenticado |

As rotas protegidas esperam o token no cabeçalho `Authorization: Bearer <token>`.

Os erros seguem o formato `ProblemDetail`: 400 para dados inválidos, 401 para credenciais ou token inválidos, 404 para jogador não encontrado e 409 para e-mail ou nome de usuário já cadastrado.

### Exemplo de cadastro

Requisição:

```json
{
  "username": "jogador1",
  "email": "jogador1@example.com",
  "password": "senha-segura-123"
}
```

Resposta (`201 Created`):

```json
{
  "id": 1,
  "username": "jogador1",
  "email": "jogador1@example.com",
  "createdAt": "2026-10-06T21:00:57Z"
}
```

## Estrutura do repositório

```
├── Back-End/            API em Spring Boot
├── Front-End/           Interface em React (em breve)
├── docker-compose.yml   Banco de dados PostgreSQL
└── .env.example         Modelo das variáveis de ambiente
```

O código do back-end é organizado por funcionalidade, em `modules/player` e `modules/auth`, com as configurações gerais em `config` e o tratamento de erros em `shared`.

## Próximas etapas

- Interface em React, com telas de cadastro e login
- Tabuleiro e posicionamento dos navios
- Modo single-player, contra o computador
- Modo multiplayer em tempo real com WebSockets
- Testes automatizados