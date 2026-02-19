package br.com.senior.prompthub.domain.service.user;

import br.com.senior.prompthub.core.repository.BaseRepository;
import br.com.senior.prompthub.core.service.AbstractBaseService;
import br.com.senior.prompthub.core.service.modelmapper.ModelMapperService;
import br.com.senior.prompthub.core.service.validate.CrudInterceptor;
import br.com.senior.prompthub.domain.entity.User;
import br.com.senior.prompthub.domain.enums.EntityStatus;
import br.com.senior.prompthub.domain.enums.GlobalRole;
import br.com.senior.prompthub.domain.repository.UserRepository;
import br.com.senior.prompthub.infrastructure.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService extends AbstractBaseService<User, Long> {
    private final UserValidator userValidator;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ModelMapperService<User> modelMapperService;


    @Override
    protected BaseRepository<User, Long> getRepository() {
        return userRepository;
    }

    @Override
    protected ModelMapperService<User> getModelMapperService() {
        return modelMapperService;
    }

    @Override
    protected CrudInterceptor<User, Long> crudInterceptor() {
        return null;
    }

    @Override
    @Transactional
    public User create(User user) {
        validateUser(user);
        saveUser(user);
        return user;
    }

    @Transactional
    public void changeStatus(Long id, EntityStatus status) {
        var user = getById(id);
        if (user.getStatus().equals(EntityStatus.DELETED)) {
            throw CustomException.badRequest("Não é possível alterar o status de um usuário deletado.");
        }
        user.setStatus(status);
        userRepository.save(user);
    }

    @Transactional
    public void changeRole(Long id, GlobalRole role) {
        var user = getById(id);
        user.setRole(role);
        userRepository.save(user);
    }

    private void validateUser(User user) {
        userValidator.validateUsernameUniqueness(user.getUsername(), user.getId());
    }

    private void saveUser(User user) {
        user.clearTeams();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
