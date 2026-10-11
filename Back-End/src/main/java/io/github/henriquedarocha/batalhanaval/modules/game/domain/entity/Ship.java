package io.github.henriquedarocha.batalhanaval.modules.game.domain.entity;

import io.github.henriquedarocha.batalhanaval.modules.game.domain.model.Orientation;
import io.github.henriquedarocha.batalhanaval.modules.game.domain.model.ShipType;
import io.github.henriquedarocha.batalhanaval.modules.game.domain.model.Side;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ships")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Side side;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ShipType type;

    @Column(nullable = false)
    private int startRow;

    @Column(nullable = false)
    private int startCol;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Orientation orientation;

    public Ship(Game game, Side side, ShipType type, int startRow, int startCol, Orientation orientation) {
        this.game = game;
        this.side = side;
        this.type = type;
        this.startRow = startRow;
        this.startCol = startCol;
        this.orientation = orientation;
    }
}