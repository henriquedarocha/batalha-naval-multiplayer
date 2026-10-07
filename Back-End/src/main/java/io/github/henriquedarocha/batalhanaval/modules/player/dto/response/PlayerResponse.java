package io.github.henriquedarocha.batalhanaval.modules.player.dto.response;

import io.github.henriquedarocha.batalhanaval.modules.player.domain.entity.Player;

import java.time.Instant;

public record PlayerResponse(

        Long id,
        String username,
        String email,
        Instant createdAt

) {
    public static PlayerResponse from(Player player) {
        return new PlayerResponse(
                player.getId(),
                player.getUsername(),
                player.getEmail(),
                player.getCreatedAt()
        );
    }
}