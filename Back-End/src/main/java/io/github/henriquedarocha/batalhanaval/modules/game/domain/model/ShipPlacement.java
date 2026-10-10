package io.github.henriquedarocha.batalhanaval.modules.game.domain.model;

import io.github.henriquedarocha.batalhanaval.modules.game.exception.InvalidFleetException;

public record ShipPlacement(ShipType type, Coordinate start, Orientation orientation) {

    public ShipPlacement {
        int lastRow = start.row();
        int lastCol = start.col();

        if (orientation == Orientation.HORIZONTAL) {
            lastCol = start.col() + type.getSize() - 1;
        } else {
            lastRow = start.row() + type.getSize() - 1;
        }

        if (lastRow >= Coordinate.BOARD_SIZE || lastCol >= Coordinate.BOARD_SIZE) {
            throw new InvalidFleetException(
                    "Navio de tamanho " + type.getSize()
                            + " não cabe no tabuleiro a partir da linha " + start.row()
                            + ", coluna " + start.col());
        }
    }
}