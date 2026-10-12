package io.github.henriquedarocha.batalhanaval.modules.game.dto.response;

import io.github.henriquedarocha.batalhanaval.modules.game.domain.entity.Game;
import io.github.henriquedarocha.batalhanaval.modules.game.domain.entity.Ship;
import io.github.henriquedarocha.batalhanaval.modules.game.domain.model.GameMode;
import io.github.henriquedarocha.batalhanaval.modules.game.domain.model.GameStatus;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public record GameDetailsResponse(

        Long id,
        GameMode mode,
        GameStatus status,
        boolean shipsCanTouch,
        Instant createdAt,
        List<ShipResponse> fleet

) {
    public static GameDetailsResponse from(Game game, List<Ship> ships) {
        List<ShipResponse> fleet = new ArrayList<>();
        for (Ship ship : ships) {
            fleet.add(ShipResponse.from(ship));
        }

        return new GameDetailsResponse(
                game.getId(),
                game.getMode(),
                game.getStatus(),
                game.isShipsCanTouch(),
                game.getCreatedAt(),
                fleet
        );
    }
}