package io.github.henriquedarocha.batalhanaval.modules.game.domain.model;

import io.github.henriquedarocha.batalhanaval.modules.game.exception.InvalidFleetException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class RandomFleetGenerator {

    private static final int MAX_ATTEMPTS = 10_000;

    private RandomFleetGenerator() {
    }

    public static List<ShipPlacement> generate(boolean shipsCanTouch, Random random) {
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            List<ShipPlacement> fleet = randomFleet(random);
            try {
                FleetValidator.validate(fleet, shipsCanTouch);
                return fleet;
            } catch (InvalidFleetException e) {
                // Frota inválida: o laço segue e sorteia outra.
            }
        }
        throw new IllegalStateException(
                "Não foi possível sortear uma frota válida em " + MAX_ATTEMPTS + " tentativas");
    }

    private static List<ShipPlacement> randomFleet(Random random) {
        List<ShipPlacement> fleet = new ArrayList<>();
        for (ShipType type : ShipType.values()) {
            fleet.add(randomPlacement(type, random));
        }
        return fleet;
    }

    private static ShipPlacement randomPlacement(ShipType type, Random random) {
        Orientation orientation = random.nextBoolean() ? Orientation.HORIZONTAL : Orientation.VERTICAL;
        int maxStart = Coordinate.BOARD_SIZE - type.getSize();
        int row;
        int col;

        if (orientation == Orientation.HORIZONTAL) {
            row = random.nextInt(Coordinate.BOARD_SIZE);
            col = random.nextInt(maxStart + 1);
        } else {
            row = random.nextInt(maxStart + 1);
            col = random.nextInt(Coordinate.BOARD_SIZE);
        }

        return new ShipPlacement(type, new Coordinate(row, col), orientation);
    }
}
