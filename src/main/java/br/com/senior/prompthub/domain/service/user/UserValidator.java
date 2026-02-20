package br.com.senior.prompthub.domain.service.user;

import br.com.senior.prompthub.domain.entity.User;
import br.com.senior.prompthub.domain.repository.UserRepository;
import br.com.senior.prompthub.infrastructure.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserValidator {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public void validateUser(User user) {
        validateUsernameUniqueness(user.getUsername(), user.getId());
        validateEmailUniqueness(user.getEmail(), user.getId());
    }

    private void validateUsernameUniqueness(String name, long id) {
        if (userRepository.existsByUsernameAndIdNot(name, id)) {
            throw CustomException.badRequest("Já existe o username: " + name);
        }
    }

    private void validateEmailUniqueness(String email, long id) {
        if (userRepository.existsByEmailAndIdNot(email, id)) {
            throw CustomException.badRequest("Já existe o email: " + email);
        }
    }
}
