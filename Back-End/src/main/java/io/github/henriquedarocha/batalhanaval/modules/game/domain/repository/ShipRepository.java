package io.github.henriquedarocha.batalhanaval.modules.game.domain.repository;

import io.github.henriquedarocha.batalhanaval.modules.game.domain.entity.Ship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipRepository extends JpaRepository<Ship, Long> {

}
