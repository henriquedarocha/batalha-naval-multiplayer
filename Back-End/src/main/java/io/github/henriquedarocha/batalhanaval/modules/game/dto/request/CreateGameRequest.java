package io.github.henriquedarocha.batalhanaval.modules.game.dto.request;

import io.github.henriquedarocha.batalhanaval.modules.game.domain.model.GameMode;
import jakarta.validation.constraints.*;

public record CreateGameRequest(

    @NotNull GameMode mode,
    @NotNull Boolean shipsCanTouch

) {}