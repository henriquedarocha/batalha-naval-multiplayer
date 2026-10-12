package io.github.henriquedarocha.batalhanaval.modules.game.service;

import io.github.henriquedarocha.batalhanaval.modules.game.domain.entity.Game;
import io.github.henriquedarocha.batalhanaval.modules.game.domain.entity.Ship;
import io.github.henriquedarocha.batalhanaval.modules.game.domain.model.*;
import io.github.henriquedarocha.batalhanaval.modules.game.domain.repository.GameRepository;
import io.github.henriquedarocha.batalhanaval.modules.game.domain.repository.ShipRepository;
import io.github.henriquedarocha.batalhanaval.modules.game.dto.request.CreateGameRequest;
import io.github.henriquedarocha.batalhanaval.modules.game.dto.request.PlaceFleetRequest;
import io.github.henriquedarocha.batalhanaval.modules.game.dto.request.ShipPlacementRequest;
import io.github.henriquedarocha.batalhanaval.modules.game.dto.response.GameDetailsResponse;
import io.github.henriquedarocha.batalhanaval.modules.game.dto.response.GameResponse;
import io.github.henriquedarocha.batalhanaval.modules.game.exception.GameNotFoundException;
import io.github.henriquedarocha.batalhanaval.modules.game.exception.GameNotPlacingShipsException;
import io.github.henriquedarocha.batalhanaval.modules.game.exception.UnsupportedGameModeException;
import io.github.henriquedarocha.batalhanaval.modules.player.domain.entity.Player;
import io.github.henriquedarocha.batalhanaval.modules.player.domain.repository.PlayerRepository;
import io.github.henriquedarocha.batalhanaval.modules.player.exception.PlayerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final ShipRepository shipRepository;
    private final PlayerRepository playerRepository;
    private final Random random = new Random();

    @Transactional
    public GameResponse create(Long playerId, CreateGameRequest request) {
        if (request.mode() == GameMode.MULTIPLAYER) {
            throw new UnsupportedGameModeException();
        }

        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException());

        Game game = new Game(request.mode(), request.shipsCanTouch(), player);
        Game savedGame = gameRepository.save(game);

        List<ShipPlacement> computerFleet = RandomFleetGenerator.generate(request.shipsCanTouch(), random);
        for (ShipPlacement placement : computerFleet) {
            shipRepository.save(toEntity(savedGame, Side.PLAYER_TWO, placement));
        }

        return GameResponse.from(savedGame);
    }

    @Transactional
    public GameDetailsResponse placeFleet(Long playerId, Long gameId, PlaceFleetRequest request) {
        Game game = gameRepository.findByIdAndPlayerOneId(gameId, playerId)
                .orElseThrow(() -> new GameNotFoundException());

        if (game.getStatus() != GameStatus.PLACING_SHIPS) {
            throw new GameNotPlacingShipsException();
        }

        List<ShipPlacement> fleet = new ArrayList<>();
        for (ShipPlacementRequest shipRequest : request.ships()) {
            fleet.add(toPlacement(shipRequest));
        }
        FleetValidator.validate(fleet, game.isShipsCanTouch());

        List<Ship> savedShips = new ArrayList<>();
        for (ShipPlacement placement : fleet) {
            savedShips.add(shipRepository.save(toEntity(game, Side.PLAYER_ONE, placement)));
        }

        game.startBattle();

        return GameDetailsResponse.from(game, savedShips);
    }

    private Ship toEntity(Game game, Side side, ShipPlacement placement) {
        return new Ship(
                game,
                side,
                placement.type(),
                placement.start().row(),
                placement.start().col(),
                placement.orientation()
        );
    }

    private ShipPlacement toPlacement(ShipPlacementRequest request) {
        return new ShipPlacement(
                request.type(),
                new Coordinate(request.row(), request.col()),
                request.orientation()
        );
    }
}