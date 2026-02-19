package br.com.senior.prompthub.domain.service.auth;

import br.com.senior.prompthub.domain.dto.auth.ChangePasswordInput;
import br.com.senior.prompthub.domain.dto.auth.LoginInput;
import br.com.senior.prompthub.domain.entity.User;
import br.com.senior.prompthub.domain.repository.UserRepository;
import br.com.senior.prompthub.domain.service.user.UserService;
import br.com.senior.prompthub.infrastructure.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public String authenticate(LoginInput loginInput) {
        var user = getUserByUsername(loginInput.getUsername());

        // Compara senha (texto plano) com hash do banco
        assertMatchPassword(loginInput.getPassword(), user.getPassword());

        return "Token JWT aqui";
    }

    @Transactional
    public void changePassword(ChangePasswordInput changePasswordInput) {
        var user = getUserByUsername(changePasswordInput.getUsername());
        assertMatchPassword(changePasswordInput.getPassword(), user.getPassword());
        user.setPassword(passwordEncoder.encode(changePasswordInput.getNewPassword()));
        userRepository.save(user);
    }

    private void assertMatchPassword(String loginPassword, String userPassword) {
        if (!passwordEncoder.matches(loginPassword, userPassword)) {
            throw CustomException.badRequest("Senha incorreta");
        }
    }

    private User getUserByUsername(String username) {
        return userRepository.findByUsernameOrEmail(username)
                .orElseThrow(() -> CustomException.notFound("Usuário informado não encontrado"));
    }
}
