package io.github.henriquedarocha.batalhanaval.modules.game.dto.response;

import io.github.henriquedarocha.batalhanaval.modules.game.domain.entity.Ship;
import io.github.henriquedarocha.batalhanaval.modules.game.domain.model.Orientation;
import io.github.henriquedarocha.batalhanaval.modules.game.domain.model.ShipType;

public record ShipResponse(

        ShipType type,
        int row,
        int col,
        Orientation orientation

) {
    public static ShipResponse from(Ship ship) {
        return new ShipResponse(
                ship.getType(),
                ship.getStartRow(),
                ship.getStartCol(),
                ship.getOrientation()
        );
    }
}