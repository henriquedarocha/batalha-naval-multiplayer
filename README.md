# Batalha Naval Multiplayer

Jogo de Batalha Naval com dois modos: single-player, contra o computador, e multiplayer em tempo real entre dois jogadores. O projeto faz parte do meu portfólio full-stack e tem dois objetivos de estudo: construir uma API em Java com Spring Boot seguindo boas práticas de arquitetura e segurança, e aprender React e comunicação em tempo real com WebSockets.

## Status

Em desenvolvimento. A autenticação (back-end e front-end) e o motor do jogo no back-end estão concluídos.

**Back-end**

- Cadastro de jogadores, com validação dos dados e senha armazenada como hash BCrypt
- Login com emissão de token JWT
- Rota protegida que identifica o jogador pelo token
- Respostas de erro padronizadas
- Regras do jogo em Java puro (tabuleiro, tipos de navio, posicionamento e validação da frota), cobertas por testes unitários
- Criação de partida single-player, com a frota do computador sorteada pelo servidor
- Posicionamento da frota do jogador, validado no servidor
- Consulta da partida, que devolve apenas a frota do próprio jogador

**Front-end**

- Telas de cadastro, login e área do jogador em React
- Erros da API exibidos ao lado de cada campo, e mensagens diferenciadas por tipo (erro, sucesso e aviso)
- Sessão guardada no navegador, com retorno automático ao login quando o token expira ou é inválido
- Identidade visual própria, com foco visível para quem navega pelo teclado

A próxima fase é o tabuleiro na interface: desenhar o tabuleiro em React, posicionar os navios na tela e enviar a frota para a API.

## Tecnologias

- **Back-end:** Java 17, Spring Boot 4, Spring Security com JWT, Spring Data JPA, PostgreSQL, Flyway, Docker e JUnit 5
- **Front-end:** React 19, Vite, React Router e ESLint
- **Em breve:** WebSockets com STOMP

## Como executar

Pré-requisitos: Java 17, Node.js em versão LTS recente (o projeto foi desenvolvido com o Node 24), Docker e Git.

### 1. Banco de dados e API

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

Para rodar os testes, a partir da pasta `Back-End` e com o banco em execução:

```bash
./mvnw test
```

Os testes das regras do jogo não dependem do banco; o teste que sobe a aplicação inteira, sim.

### 2. Interface

Em outro terminal, a partir da raiz do repositório:

```bash
cd Front-End
npm install
npm run dev
```

A interface fica disponível em `http://localhost:5173`. O endereço da API é lido da variável `VITE_API_URL`, definida em `Front-End/.env.development`, que já aponta para `http://localhost:8080`. A API aceita requisições apenas dessa origem (CORS), então use a porta 5173.

## Telas

| Rota | Tela | Acesso |
|---|---|---|
| `/` | Redireciona para `/home`, se houver sessão, ou para `/login` | Pública |
| `/register` | Cadastro de jogador | Pública |
| `/login` | Login | Pública |
| `/home` | Área do jogador | Exige login |

Sem um token válido, a área do jogador devolve o usuário ao login com um aviso.

## Regras do jogo

- Tabuleiro de 10 × 10. Na API, as casas são indicadas por `row` e `col`, de 0 a 9; letras e números (A–J, 1–10) aparecem só na tela.
- Cada jogador tem a frota clássica, com um navio de cada tipo: `CARRIER` (5 casas), `BATTLESHIP` (4), `CRUISER` (3), `SUBMARINE` (3) e `DESTROYER` (2).
- Cada navio é descrito por tipo, casa inicial e orientação (`HORIZONTAL` ou `VERTICAL`). A casa inicial é a ponta de cima ou da esquerda, e o servidor calcula as demais casas. Assim não existe navio diagonal nem com buraco.
- A frota é recusada se algum navio sair do tabuleiro, se dois navios ocuparem a mesma casa ou se faltar ou sobrar algum tipo.
- Navios encostados são uma regra escolhida na criação da partida (`shipsCanTouch`). Quando proibidos, nenhum navio pode ter outro nas oito casas vizinhas, inclusive na diagonal.

## API

| Método | Rota | Autenticação | Descrição |
|---|---|---|---|
| POST | `/api/players` | Não | Cadastra um jogador |
| POST | `/api/auth/login` | Não | Valida as credenciais e devolve um token JWT |
| GET | `/api/players/me` | Bearer token | Devolve os dados do jogador autenticado |
| POST | `/api/games` | Bearer token | Cria uma partida single-player; a frota do computador é sorteada na hora |
| POST | `/api/games/{id}/fleet` | Bearer token | Posiciona a frota do jogador e inicia a partida |
| GET | `/api/games/{id}` | Bearer token | Devolve a partida e a frota do próprio jogador |

As rotas protegidas esperam o token no cabeçalho `Authorization: Bearer <token>`.

Os erros seguem o formato `ProblemDetail`:

- 400 para dados inválidos (com o campo de cada erro), frota inválida (com o motivo), corpo da requisição ilegível ou parâmetro inválido na URL;
- 401 para credenciais ou token inválidos;
- 404 para jogador ou partida não encontrados;
- 409 para e-mail ou nome de usuário já cadastrado, ou para frota enviada a uma partida que já começou.

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

### Exemplo de posicionamento da frota

`POST /api/games/1/fleet`, com o token do dono da partida:

```json
{
  "ships": [
    { "type": "CARRIER",    "row": 0, "col": 0, "orientation": "HORIZONTAL" },
    { "type": "BATTLESHIP", "row": 2, "col": 0, "orientation": "HORIZONTAL" },
    { "type": "CRUISER",    "row": 4, "col": 0, "orientation": "HORIZONTAL" },
    { "type": "SUBMARINE",  "row": 6, "col": 0, "orientation": "HORIZONTAL" },
    { "type": "DESTROYER",  "row": 8, "col": 0, "orientation": "HORIZONTAL" }
  ]
}
```

Resposta (`200 OK`), no mesmo formato do `GET /api/games/{id}`:

```json
{
  "id": 1,
  "mode": "SINGLE_PLAYER",
  "status": "IN_PROGRESS",
  "shipsCanTouch": false,
  "createdAt": "2026-10-11T23:34:52Z",
  "fleet": [
    { "type": "CARRIER", "row": 0, "col": 0, "orientation": "HORIZONTAL" }
  ]
}
```

(A lista `fleet` traz os cinco navios; aqui aparece só o primeiro.)

Uma frota com dois navios na mesma casa recebe `400` com o motivo no `detail`, por exemplo "Navios sobrepostos na linha 0, coluna 3".

## Decisões de projeto

### O servidor é a autoridade do jogo

Tipos de navio, validação do posicionamento, turnos, acertos e vitória são decididos em Java, no servidor. O front-end apenas exibe o estado do jogo e envia as ações do jogador. A posição dos navios de um jogador nunca é enviada ao adversário.

### Regras do jogo isoladas do framework

As regras ficam em classes Java puras (`modules/game/domain/model`), sem Spring nem banco de dados, e por isso são testadas com JUnit em milissegundos. O mesmo validador da frota é usado para o jogador e para o sorteio da frota do computador. As tabelas guardam apenas o que essas regras já aprovaram, e as restrições do banco (`CHECK`, `UNIQUE`) funcionam como uma segunda rede de segurança.

### Partida de outro jogador responde 404

Uma partida que pertence a outro jogador recebe a mesma resposta de uma partida inexistente (`404`). Com um `403`, quem testasse ids em sequência descobriria quais partidas existem. A verificação é feita numa única consulta, que busca a partida pelo id e pelo dono ao mesmo tempo.

### Onde o token fica guardado

O token JWT é guardado no `localStorage` do navegador. Essa é uma troca consciente: o `localStorage` pode ser lido por scripts da página e, portanto, fica exposto a ataques de XSS. A alternativa, um cookie `HttpOnly`, exigiria mudar o modelo de autenticação e reativar a proteção contra CSRF.

Para reduzir o risco:

- o React escapa por padrão todo texto exibido na tela, o que dificulta a injeção de scripts;
- o token expira em 60 minutos;
- o servidor confere a assinatura e a validade do token em toda requisição.

Como a API não guarda sessão (`STATELESS`), sair da conta apenas apaga o token do navegador. O token continua válido no servidor até expirar, o que é aceitável para o escopo deste projeto.

### Validação em duas camadas

A validação do navegador (como o campo do tipo e-mail) é uma cortesia para o jogador. A garantia é a validação do back-end, e as mensagens de erro exibidas na tela vêm da própria API.

## Estrutura do repositório

```
├── Back-End/            API em Spring Boot
├── Front-End/           Interface em React com Vite
│   └── src/
│       ├── pages/       Uma tela por arquivo (cadastro, login, área do jogador)
│       ├── App.jsx      Declaração das rotas
│       ├── main.jsx     Ponto de entrada da aplicação
│       └── index.css    Estilo global e paleta de cores
├── docker-compose.yml   Banco de dados PostgreSQL
└── .env.example         Modelo das variáveis de ambiente
```

O código do back-end é organizado por funcionalidade, em `modules/player`, `modules/auth` e `modules/game`, com as configurações gerais em `config` e o tratamento de erros em `shared`. As tabelas são criadas por migrations do Flyway, em `Back-End/src/main/resources/db/migration`.

## Próximas etapas

- Tabuleiro e posicionamento dos navios na interface
- Modo single-player, contra o computador
- Modo multiplayer em tempo real com WebSockets
- Testes automatizados
