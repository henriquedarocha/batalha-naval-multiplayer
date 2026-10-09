# Batalha Naval Multiplayer

Jogo de Batalha Naval com dois modos: single-player, contra o computador, e multiplayer em tempo real entre dois jogadores. O projeto faz parte do meu portfólio full-stack e tem dois objetivos de estudo: construir uma API em Java com Spring Boot seguindo boas práticas de arquitetura e segurança, e aprender React e comunicação em tempo real com WebSockets.

## Status

Em desenvolvimento. As duas primeiras fases estão concluídas.

**Back-end**

- Cadastro de jogadores, com validação dos dados e senha armazenada como hash BCrypt
- Login com emissão de token JWT
- Rota protegida que identifica o jogador pelo token
- Respostas de erro padronizadas

**Front-end**

- Telas de cadastro, login e área do jogador em React
- Erros da API exibidos ao lado de cada campo, e mensagens diferenciadas por tipo (erro, sucesso e aviso)
- Sessão guardada no navegador, com retorno automático ao login quando o token expira ou é inválido
- Identidade visual própria, com foco visível para quem navega pelo teclado

A próxima fase é o motor do jogo: tabuleiro, tipos de navio e regras de posicionamento.

## Tecnologias

- **Back-end:** Java 17, Spring Boot 4, Spring Security com JWT, PostgreSQL, Flyway e Docker
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

## Decisões de projeto

### O servidor é a autoridade do jogo

Tipos de navio, validação do posicionamento, turnos, acertos e vitória são decididos em Java, no servidor. O front-end apenas exibe o estado do jogo e envia as ações do jogador. A posição dos navios de um jogador nunca é enviada ao adversário.

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

O código do back-end é organizado por funcionalidade, em `modules/player` e `modules/auth`, com as configurações gerais em `config` e o tratamento de erros em `shared`.

## Próximas etapas

- Tabuleiro e posicionamento dos navios
- Modo single-player, contra o computador
- Modo multiplayer em tempo real com WebSockets
- Testes automatizados
