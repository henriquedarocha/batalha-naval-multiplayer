package io.github.henriquedarocha.batalhanaval.modules.auth.controller;

import io.github.henriquedarocha.batalhanaval.modules.auth.dto.request.LoginRequest;
import io.github.henriquedarocha.batalhanaval.modules.auth.dto.response.LoginResponse;
import io.github.henriquedarocha.batalhanaval.modules.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest login) {
        LoginResponse response = authService.login(login);

        return ResponseEntity.ok(response);
    }
}