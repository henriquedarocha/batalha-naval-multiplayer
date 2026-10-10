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
}