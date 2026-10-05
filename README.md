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