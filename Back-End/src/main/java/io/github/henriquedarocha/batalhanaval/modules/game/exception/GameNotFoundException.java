package io.github.henriquedarocha.batalhanaval.modules.game.exception;

public class GameNotFoundException extends RuntimeException {

    public GameNotFoundException() {
        super("Partida não encontrada");
    }
}