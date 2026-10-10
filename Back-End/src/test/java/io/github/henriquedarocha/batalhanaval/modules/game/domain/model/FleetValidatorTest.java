package io.github.henriquedarocha.batalhanaval.modules.game.domain.model;

import io.github.henriquedarocha.batalhanaval.modules.game.exception.InvalidFleetException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FleetValidatorTest {

    private static ShipPlacement ship(ShipType type, int row, int col, Orientation orientation) {
        return new ShipPlacement(type, new Coordinate(row, col), orientation);
    }

    private static List<ShipPlacement> validFleet() {
        return List.of(
                ship(ShipType.CARRIER, 0, 0, Orientation.HORIZONTAL),
                ship(ShipType.BATTLESHIP, 2, 0, Orientation.HORIZONTAL),
                ship(ShipType.CRUISER, 4, 0, Orientation.HORIZONTAL),
                ship(ShipType.SUBMARINE, 6, 0, Orientation.HORIZONTAL),
                ship(ShipType.DESTROYER, 8, 0, Orientation.HORIZONTAL));
    }

    @Test
    void validFleetIsAccepted() {
        assertDoesNotThrow(() -> FleetValidator.validate(validFleet(), false));
    }

    @Test
    void fleetWithMissingShipIsRejected() {
        List<ShipPlacement> fleet = validFleet().subList(0, 4);

        assertThrows(InvalidFleetException.class, () -> FleetValidator.validate(fleet, true));
    }

    @Test
    void fleetWithRepeatedTypeIsRejected() {
        List<ShipPlacement> fleet = List.of(
                ship(ShipType.CARRIER, 0, 0, Orientation.HORIZONTAL),
                ship(ShipType.BATTLESHIP, 2, 0, Orientation.HORIZONTAL),
                ship(ShipType.CRUISER, 4, 0, Orientation.HORIZONTAL),
                ship(ShipType.CRUISER, 6, 0, Orientation.HORIZONTAL),
                ship(ShipType.DESTROYER, 8, 0, Orientation.HORIZONTAL));

        assertThrows(InvalidFleetException.class, () -> FleetValidator.validate(fleet, false));
    }
}