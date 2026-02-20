package br.com.senior.prompthub.domain.service.auth;

import br.com.senior.prompthub.core.service.modelmapper.AbstractModelMapperService;
import br.com.senior.prompthub.domain.dto.auth.ChangePasswordInput;
import br.com.senior.prompthub.domain.dto.auth.RegisterInput;
import br.com.senior.prompthub.domain.entity.User;
import br.com.senior.prompthub.domain.repository.UserRepository;
import br.com.senior.prompthub.domain.service.user.UserValidator;
import br.com.senior.prompthub.infrastructure.exception.CustomException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserValidator userValidator;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AbstractModelMapperService modelMapperService;

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


    @Transactional
    public void register(@Valid RegisterInput registerInput) {
        var user = modelMapperService.toObject(User.class, registerInput);
        userValidator.validateUser(user);
        user.setPassword(passwordEncoder.encode(registerInput.getPassword()));
        userRepository.save(user);
    }

    private void assertMatchPassword(String loginPassword, String userPassword) {
        if (!passwordEncoder.matches(loginPassword, userPassword)) {
            throw CustomException.badRequest("Senha incorreta");
        }
    }
}
