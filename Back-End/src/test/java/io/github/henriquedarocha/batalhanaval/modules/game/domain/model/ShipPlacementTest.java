package io.github.henriquedarocha.batalhanaval.modules.game.domain.model;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ShipPlacementTest {

    @Test
    void horizontalShipOccupiesCellsToTheRight() {
        ShipPlacement ship = new ShipPlacement(ShipType.DESTROYER, new Coordinate(2, 5), Orientation.HORIZONTAL);

        List<Coordinate> expected = List.of(new Coordinate(2, 5), new Coordinate(2, 6));

        assertEquals(expected, ship.cells());
    }

    @Test
    void verticalShipOccupiesCellsBelow() {
        ShipPlacement ship = new ShipPlacement(ShipType.DESTROYER, new Coordinate(2, 5), Orientation.VERTICAL);

        List<Coordinate> expected = List.of(new Coordinate(2, 5), new Coordinate(3, 5));

        assertEquals(expected, ship.cells());
    }
}