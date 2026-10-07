package io.github.henriquedarocha.batalhanaval.modules.auth.service;

import io.github.henriquedarocha.batalhanaval.modules.auth.dto.request.LoginRequest;
import io.github.henriquedarocha.batalhanaval.modules.auth.exception.InvalidCredentialsException;
import io.github.henriquedarocha.batalhanaval.modules.player.domain.entity.Player;
import io.github.henriquedarocha.batalhanaval.modules.player.domain.repository.PlayerRepository;
import io.github.henriquedarocha.batalhanaval.modules.player.dto.response.PlayerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;

    public PlayerResponse login(LoginRequest request) {
        Player player = playerRepository.findByEmail(request.email())
                .orElseThrow(() -> new InvalidCredentialsException());

        if (!passwordEncoder.matches(request.password(), player.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }

        return new PlayerResponse(
                player.getId(),
                player.getUsername(),
                player.getEmail(),
                player.getCreatedAt()
        );
    }
}