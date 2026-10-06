package io.github.henriquedarocha.batalhanaval.modules.player.dto.response;

import java.time.Instant;

public record PlayerResponse(

        Long id,
        String username,
        String email,
        Instant createdAt

) {}