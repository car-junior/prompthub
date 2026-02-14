package br.com.senior.prompthub.domain.service.user;

import br.com.senior.prompthub.domain.repository.UserRepository;
import br.com.senior.prompthub.infrastructure.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {
    private final UserRepository userRepository;

    public void validateUsernameUniqueness(String name, long id) {
        if (userRepository.existsByUsernameAndIdNot(name, id)) {
            throw CustomException.badRequest("Já existe o username: " + name);
        }
    }

    public void validateEmailUniqueness(String email, long id) {
        if (userRepository.existsByEmailAndIdNot(email, id)) {
            throw CustomException.badRequest("Já existe o email: " + email);
        }
    }
}
