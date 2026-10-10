package io.github.henriquedarocha.batalhanaval.modules.game.domain.model;

public record Coordinate(int row, int col) {

    public static final int BOARD_SIZE = 10;

    public Coordinate {
        if (row < 0 || row >= BOARD_SIZE) {
            throw new IllegalArgumentException("Linha fora do tabuleiro: " + row);
        }
        if (col < 0 || col >= BOARD_SIZE) {
            throw new IllegalArgumentException("Coluna fora do tabuleiro: " + col);
        }
    }
}