CREATE TABLE games
(
    id              BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    mode            VARCHAR(20)              NOT NULL CHECK (mode IN ('SINGLE_PLAYER', 'MULTIPLAYER')),
    status          VARCHAR(20)              NOT NULL CHECK (status IN ('PLACING_SHIPS', 'IN_PROGRESS', 'FINISHED')),
    ships_can_touch BOOLEAN                  NOT NULL,
    player_one_id   BIGINT                   NOT NULL REFERENCES players (id),
    created_at      TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);

CREATE TABLE ships
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    game_id     BIGINT      NOT NULL REFERENCES games (id) ON DELETE CASCADE,
    side        VARCHAR(20) NOT NULL CHECK (side IN ('PLAYER_ONE', 'PLAYER_TWO')),
    type        VARCHAR(20) NOT NULL CHECK (type IN ('CARRIER', 'BATTLESHIP', 'CRUISER', 'SUBMARINE', 'DESTROYER')),
    start_row   INTEGER     NOT NULL CHECK (start_row BETWEEN 0 AND 9),
    start_col   INTEGER     NOT NULL CHECK (start_col BETWEEN 0 AND 9),
    orientation VARCHAR(10) NOT NULL CHECK (orientation IN ('HORIZONTAL', 'VERTICAL')),
    UNIQUE (game_id, side, type)
);