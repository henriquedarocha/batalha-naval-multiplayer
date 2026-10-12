package io.github.henriquedarocha.batalhanaval.modules.game.controller;

import io.github.henriquedarocha.batalhanaval.modules.game.dto.request.CreateGameRequest;
import io.github.henriquedarocha.batalhanaval.modules.game.dto.request.PlaceFleetRequest;
import io.github.henriquedarocha.batalhanaval.modules.game.dto.response.GameDetailsResponse;
import io.github.henriquedarocha.batalhanaval.modules.game.dto.response.GameResponse;
import io.github.henriquedarocha.batalhanaval.modules.game.service.GameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping
    public ResponseEntity<GameResponse> createGame(@AuthenticationPrincipal Jwt jwt,
                                                   @Valid @RequestBody CreateGameRequest request) {
        Long playerId = Long.valueOf(jwt.getSubject());
        GameResponse response = gameService.create(playerId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{id}/fleet")
    public ResponseEntity<GameDetailsResponse> placeFleet(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id,
                                                          @Valid @RequestBody PlaceFleetRequest request) {
        Long playerId = Long.valueOf(jwt.getSubject());
        GameDetailsResponse response = gameService.placeFleet(playerId, id, request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameDetailsResponse> getGame(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id) {
        Long playerId = Long.valueOf(jwt.getSubject());
        GameDetailsResponse response = gameService.getGame(playerId, id);

        return ResponseEntity.ok(response);
    }
}