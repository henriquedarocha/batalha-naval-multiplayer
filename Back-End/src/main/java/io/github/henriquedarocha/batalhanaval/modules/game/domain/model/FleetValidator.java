package io.github.henriquedarocha.batalhanaval.modules.game.domain.model;

import io.github.henriquedarocha.batalhanaval.modules.game.exception.InvalidFleetException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class FleetValidator {

    private FleetValidator() {
    }

    public static void validate(List<ShipPlacement> ships, boolean shipsCanTouch) {
        validateComposition(ships);
        validateNoOverlap(ships);
    }

    private static void validateComposition(List<ShipPlacement> ships) {
        int expectedShips = ShipType.values().length;

        if (ships.size() != expectedShips) {
            throw new InvalidFleetException("A frota deve ter exatamente " + expectedShips + " navios");
        }

        Set<ShipType> seenTypes = new HashSet<>();
        for (ShipPlacement ship : ships) {
            if (!seenTypes.add(ship.type())) {
                throw new InvalidFleetException("Tipo de navio repetido na frota: " + ship.type());
            }
        }
    }

    private static void validateNoOverlap(List<ShipPlacement> ships) {
        Set<Coordinate> occupiedCells = new HashSet<>();
        for (ShipPlacement ship : ships) {
            for (Coordinate cell : ship.cells()) {
                if (!occupiedCells.add(cell)) {
                    throw new InvalidFleetException("Navios sobrepostos na linha " + cell.row() + ", coluna " + cell.col());
                }
            }
        }
    }
}