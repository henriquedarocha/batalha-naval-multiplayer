package io.github.henriquedarocha.batalhanaval.modules.player.service;

import io.github.henriquedarocha.batalhanaval.modules.player.domain.entity.Player;
import io.github.henriquedarocha.batalhanaval.modules.player.domain.repository.PlayerRepository;
import io.github.henriquedarocha.batalhanaval.modules.player.dto.request.RegisterPlayerRequest;
import io.github.henriquedarocha.batalhanaval.modules.player.dto.response.PlayerResponse;
import io.github.henriquedarocha.batalhanaval.modules.player.exception.PlayerAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public PlayerResponse register(RegisterPlayerRequest request) {
        if (playerRepository.existsByEmail(request.email())) {
            throw new PlayerAlreadyExistsException("E-mail já cadastrado");
        }

        if (playerRepository.existsByUsername(request.username())) {
            throw new PlayerAlreadyExistsException("Nome de usuário já cadastrado");
        }

        String passwordHash = passwordEncoder.encode(request.password());

        Player player = new Player(request.username(), request.email(), passwordHash);
        Player savedPlayer = playerRepository.save(player);

        return new PlayerResponse(
                savedPlayer.getId(),
                savedPlayer.getUsername(),
                savedPlayer.getEmail(),
                savedPlayer.getCreatedAt()
        );
    }
}