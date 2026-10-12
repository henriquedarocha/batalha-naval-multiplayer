package io.github.henriquedarocha.batalhanaval.modules.game.dto.request;

import io.github.henriquedarocha.batalhanaval.modules.game.domain.model.Coordinate;
import io.github.henriquedarocha.batalhanaval.modules.game.domain.model.Orientation;
import io.github.henriquedarocha.batalhanaval.modules.game.domain.model.ShipType;
import jakarta.validation.constraints.*;

public record ShipPlacementRequest(

    @NotNull ShipType type,
    @NotNull @Min(0) @Max(Coordinate.BOARD_SIZE - 1) Integer row,
    @NotNull @Min(0) @Max(Coordinate.BOARD_SIZE - 1) Integer col,
    @NotNull Orientation orientation

) {}