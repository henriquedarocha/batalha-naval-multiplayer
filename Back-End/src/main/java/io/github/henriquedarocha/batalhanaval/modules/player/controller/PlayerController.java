package io.github.henriquedarocha.batalhanaval.modules.player.controller;

import io.github.henriquedarocha.batalhanaval.modules.player.dto.request.RegisterPlayerRequest;
import io.github.henriquedarocha.batalhanaval.modules.player.dto.response.PlayerResponse;
import io.github.henriquedarocha.batalhanaval.modules.player.service.PlayerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}