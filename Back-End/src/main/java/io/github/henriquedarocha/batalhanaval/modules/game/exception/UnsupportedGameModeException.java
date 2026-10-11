package io.github.henriquedarocha.batalhanaval.modules.game.exception;

public class UnsupportedGameModeException extends RuntimeException {

    public UnsupportedGameModeException() {
        super("Modo multiplayer ainda não está disponível");
    }
}