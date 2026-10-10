package io.github.henriquedarocha.batalhanaval.modules.game.domain.model;

import io.github.henriquedarocha.batalhanaval.modules.game.exception.InvalidFleetException;

import java.util.ArrayList;
import java.util.List;

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

    public List<Coordinate> cells() {
        List<Coordinate> cells = new ArrayList<>();

        for (int i = 0; i < type.getSize(); i++) {
            if (orientation == Orientation.HORIZONTAL) {
                cells.add(new Coordinate(start.row(), start.col() + i));
            } else {
                cells.add(new Coordinate(start.row() + i, start.col()));
            }
        }

        return cells;
    }
}