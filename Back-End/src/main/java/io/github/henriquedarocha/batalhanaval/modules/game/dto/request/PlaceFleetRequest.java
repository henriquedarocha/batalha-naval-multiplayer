package io.github.henriquedarocha.batalhanaval.modules.game.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PlaceFleetRequest(

        @NotNull List<@NotNull @Valid ShipPlacementRequest> ships

) {}
