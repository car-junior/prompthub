package br.com.senior.prompthub.domain.controller;

import br.com.senior.prompthub.config.security.JwtTokenService;
import br.com.senior.prompthub.domain.dto.auth.ChangePasswordInput;
import br.com.senior.prompthub.domain.dto.auth.LoginInput;
import br.com.senior.prompthub.domain.dto.auth.LoginResponse;
import br.com.senior.prompthub.domain.service.auth.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtTokenService jwtTokenService;
    private final AuthenticationService authenticationService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginInput loginInput) {
        var usernameAuthentication = new UsernamePasswordAuthenticationToken(loginInput.getUsername(), loginInput.getPassword());
        authenticationManager.authenticate(usernameAuthentication);
        var token = jwtTokenService.generateToken(loginInput.getUsername());
        return ResponseEntity.ok(LoginResponse.builder().token(token).build());
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordInput changePasswordInput) {
        authenticationService.changePassword(changePasswordInput);
        return ResponseEntity.noContent().build();
    }
}
