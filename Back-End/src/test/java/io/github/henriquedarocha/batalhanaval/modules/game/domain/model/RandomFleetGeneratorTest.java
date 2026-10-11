package io.github.henriquedarocha.batalhanaval.modules.game.domain.model;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class RandomFleetGeneratorTest {

    @Test
    void generatedFleetIsValidWhenShipsCanTouch() {
        Random random = new Random(42);

        for (int i = 0; i < 100; i++) {
            List<ShipPlacement> fleet = RandomFleetGenerator.generate(true, random);
            assertDoesNotThrow(() -> FleetValidator.validate(fleet, true));
        }
    }

    @Test
    void generatedFleetIsValidWhenShipsCannotTouch() {
        Random random = new Random(42);

        for (int i = 0; i < 100; i++) {
            List<ShipPlacement> fleet = RandomFleetGenerator.generate(false, random);
            assertDoesNotThrow(() -> FleetValidator.validate(fleet, false));
        }
    }
}