package io.github.henriquedarocha.batalhanaval.modules.game.exception;

public class GameNotPlacingShipsException extends RuntimeException {

    public GameNotPlacingShipsException() {
        super("A partida não está mais na fase de posicionamento");
    }
}