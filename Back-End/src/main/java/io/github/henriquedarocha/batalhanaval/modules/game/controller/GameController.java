package io.github.henriquedarocha.batalhanaval.modules.game.controller;

import io.github.henriquedarocha.batalhanaval.modules.game.dto.request.CreateGameRequest;
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
}