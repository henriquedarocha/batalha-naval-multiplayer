package io.github.henriquedarocha.batalhanaval.modules.game.dto.response;

import io.github.henriquedarocha.batalhanaval.modules.game.domain.entity.Game;
import io.github.henriquedarocha.batalhanaval.modules.game.domain.model.GameMode;
import io.github.henriquedarocha.batalhanaval.modules.game.domain.model.GameStatus;

import java.time.Instant;

public record GameResponse(

        Long id,
        GameMode mode,
        GameStatus status,
        boolean shipsCanTouch,
        Instant createdAt

) {
    public static GameResponse from(Game game) {
        return new GameResponse(
                game.getId(),
                game.getMode(),
                game.getStatus(),
                game.isShipsCanTouch(),
                game.getCreatedAt()
        );
    }
}