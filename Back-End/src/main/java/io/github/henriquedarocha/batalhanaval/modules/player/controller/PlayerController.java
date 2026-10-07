package io.github.henriquedarocha.batalhanaval.modules.player.controller;

import io.github.henriquedarocha.batalhanaval.modules.player.dto.request.RegisterPlayerRequest;
import io.github.henriquedarocha.batalhanaval.modules.player.dto.response.PlayerResponse;
import io.github.henriquedarocha.batalhanaval.modules.player.service.PlayerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @PostMapping
    public ResponseEntity<PlayerResponse> registerPlayer(@Valid @RequestBody RegisterPlayerRequest request) {
        PlayerResponse response = playerService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<PlayerResponse> getAuthenticatedPlayer(@AuthenticationPrincipal Jwt jwt) {
        Long playerId = Long.valueOf(jwt.getSubject());
        PlayerResponse response = playerService.findById(playerId);

        return ResponseEntity.ok(response);
    }
}