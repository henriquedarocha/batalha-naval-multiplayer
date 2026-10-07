package io.github.henriquedarocha.batalhanaval.modules.player.domain.repository;

import io.github.henriquedarocha.batalhanaval.modules.player.domain.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<Player> findByEmail(String email);
}
