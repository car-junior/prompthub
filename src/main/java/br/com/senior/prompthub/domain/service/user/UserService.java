package br.com.senior.prompthub.domain.service.user;

import br.com.senior.prompthub.core.repository.BaseRepository;
import br.com.senior.prompthub.core.service.AbstractBaseService;
import br.com.senior.prompthub.core.service.modelmapper.ModelMapperService;
import br.com.senior.prompthub.core.service.validate.CrudInterceptor;
import br.com.senior.prompthub.domain.entity.User;
import br.com.senior.prompthub.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService extends AbstractBaseService<User, Long> {
    private final UserRepository userRepository;
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

    @Transactional
    public void changeStatus(Long id, Boolean isActive) {
        var user = getById(id);
        user.setIsActive(isActive);
        userRepository.save(user);
    }
}
