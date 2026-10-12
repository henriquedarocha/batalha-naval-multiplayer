package io.github.henriquedarocha.batalhanaval.modules.game.domain.repository;

import io.github.henriquedarocha.batalhanaval.modules.game.domain.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findByIdAndPlayerOneId(Long id, Long playerId);
}