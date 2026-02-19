package br.com.senior.prompthub.domain.controller;

import br.com.senior.prompthub.domain.dto.auth.ChangePasswordInput;
import br.com.senior.prompthub.domain.dto.auth.LoginInput;
import br.com.senior.prompthub.domain.dto.auth.LoginResponse;
import br.com.senior.prompthub.domain.service.auth.AuthService;
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
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginInput loginInput) {
        var token = authService.authenticate(loginInput);
        // Gera token JWT ou sessão
        return ResponseEntity.ok(LoginResponse.builder().token(token).build());
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordInput changePasswordInput) {
        authService.changePassword(changePasswordInput);
        return ResponseEntity.noContent().build();
    }
}
