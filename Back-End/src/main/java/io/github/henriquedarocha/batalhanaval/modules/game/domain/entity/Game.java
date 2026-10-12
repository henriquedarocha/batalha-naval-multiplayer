package io.github.henriquedarocha.batalhanaval.modules.game.domain.entity;

import io.github.henriquedarocha.batalhanaval.modules.game.domain.model.GameMode;
import io.github.henriquedarocha.batalhanaval.modules.game.domain.model.GameStatus;
import io.github.henriquedarocha.batalhanaval.modules.player.domain.entity.Player;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "games")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private GameMode mode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private GameStatus status;

    @Column(nullable = false)
    private boolean shipsCanTouch;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "player_one_id", nullable = false)
    private Player playerOne;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    public Game(GameMode mode, boolean shipsCanTouch, Player playerOne) {
        this.mode = mode;
        this.shipsCanTouch = shipsCanTouch;
        this.playerOne = playerOne;
        this.status = GameStatus.PLACING_SHIPS;
    }

    public void startBattle() {
        if (status != GameStatus.PLACING_SHIPS) {
            throw new IllegalStateException("Partida não está em posicionamento: " + status);
        }
        this.status = GameStatus.IN_PROGRESS;
    }
}