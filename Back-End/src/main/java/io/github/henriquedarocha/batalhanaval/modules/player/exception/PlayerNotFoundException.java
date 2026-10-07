package io.github.henriquedarocha.batalhanaval.modules.player.exception;

public class PlayerNotFoundException extends RuntimeException {

    public PlayerNotFoundException() {
        super("Jogador não encontrado");
    }
}