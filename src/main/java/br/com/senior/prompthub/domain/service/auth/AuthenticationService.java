package br.com.senior.prompthub.domain.service.auth;

import br.com.senior.prompthub.domain.dto.auth.ChangePasswordInput;
import br.com.senior.prompthub.domain.entity.User;
import br.com.senior.prompthub.domain.repository.UserRepository;
import br.com.senior.prompthub.infrastructure.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void changePassword(ChangePasswordInput changePasswordInput) {
        var user = getUserByUsernameOrEmail(changePasswordInput.getUsername());
        assertMatchPassword(changePasswordInput.getPassword(), user.getPassword());
        user.setPassword(passwordEncoder.encode(changePasswordInput.getNewPassword()));
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getUserByUsernameOrEmail(String username) {
        return userRepository.findByUsernameOrEmail(username).orElseThrow(() -> CustomException.notFound("Usuário informado não encontrado"));
    }

    private void assertMatchPassword(String loginPassword, String userPassword) {
        if (!passwordEncoder.matches(loginPassword, userPassword)) {
            throw CustomException.badRequest("Senha incorreta");
        }
    }

}
