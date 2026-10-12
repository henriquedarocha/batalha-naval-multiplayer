package io.github.henriquedarocha.batalhanaval.modules.game.domain.repository;

import io.github.henriquedarocha.batalhanaval.modules.game.domain.entity.Ship;
import io.github.henriquedarocha.batalhanaval.modules.game.domain.model.Side;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShipRepository extends JpaRepository<Ship, Long> {
    List<Ship> findByGameIdAndSideOrderByIdAsc(Long gameId, Side side);
}