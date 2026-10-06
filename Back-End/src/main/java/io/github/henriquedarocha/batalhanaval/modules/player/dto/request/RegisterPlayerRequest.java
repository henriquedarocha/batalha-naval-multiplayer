package io.github.henriquedarocha.batalhanaval.modules.player.dto.request;

import jakarta.validation.constraints.*;

public record RegisterPlayerRequest(

    @NotBlank @Size(min = 3, max = 30) String username,
    @NotBlank @Email @Size(max = 255) String email,
    @NotBlank @Size(min = 8, max = 72) String password

) {}