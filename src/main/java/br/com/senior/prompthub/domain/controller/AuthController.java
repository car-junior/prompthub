package br.com.senior.prompthub.domain.controller;

import br.com.senior.prompthub.config.security.JwtTokenService;
import br.com.senior.prompthub.domain.dto.auth.ChangePasswordInput;
import br.com.senior.prompthub.domain.dto.auth.LoginInput;
import br.com.senior.prompthub.domain.dto.auth.LoginResponse;
import br.com.senior.prompthub.domain.dto.auth.RegisterInput;
import br.com.senior.prompthub.domain.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtTokenService jwtTokenService;
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginInput loginInput) {
        log.info("Tentativa de login para usuário: {}", loginInput.getUsername());
        try {
            var usernameAuthentication = new UsernamePasswordAuthenticationToken(loginInput.getUsername(), loginInput.getPassword());
            authenticationManager.authenticate(usernameAuthentication);
            log.info("Autenticação bem-sucedida para usuário: {}", loginInput.getUsername());
            var token = jwtTokenService.generateToken(loginInput.getUsername());
            log.info("Token gerado com sucesso para usuário: {}", loginInput.getUsername());
            return ResponseEntity.ok(LoginResponse.builder().token(token).build());
        } catch (Exception e) {
            log.error("Erro ao autenticar usuário: {}", loginInput.getUsername(), e);
            throw e;
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterInput registerInput) {
        authService.register(registerInput);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordInput changePasswordInput) {
        authService.changePassword(changePasswordInput);
        return ResponseEntity.noContent().build();
    }
}
