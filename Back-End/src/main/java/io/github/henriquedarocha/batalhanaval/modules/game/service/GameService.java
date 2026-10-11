package io.github.henriquedarocha.batalhanaval.modules.game.service;

import io.github.henriquedarocha.batalhanaval.modules.game.domain.entity.Game;
import io.github.henriquedarocha.batalhanaval.modules.game.domain.entity.Ship;
import io.github.henriquedarocha.batalhanaval.modules.game.domain.model.GameMode;
import io.github.henriquedarocha.batalhanaval.modules.game.domain.model.RandomFleetGenerator;
import io.github.henriquedarocha.batalhanaval.modules.game.domain.model.ShipPlacement;
import io.github.henriquedarocha.batalhanaval.modules.game.domain.model.Side;
import io.github.henriquedarocha.batalhanaval.modules.game.domain.repository.GameRepository;
import io.github.henriquedarocha.batalhanaval.modules.game.domain.repository.ShipRepository;
import io.github.henriquedarocha.batalhanaval.modules.game.dto.request.CreateGameRequest;
import io.github.henriquedarocha.batalhanaval.modules.game.dto.response.GameResponse;
import io.github.henriquedarocha.batalhanaval.modules.game.exception.UnsupportedGameModeException;
import io.github.henriquedarocha.batalhanaval.modules.player.domain.entity.Player;
import io.github.henriquedarocha.batalhanaval.modules.player.domain.repository.PlayerRepository;
import io.github.henriquedarocha.batalhanaval.modules.player.exception.PlayerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}