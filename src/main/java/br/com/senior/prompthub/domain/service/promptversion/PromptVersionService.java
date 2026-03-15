package br.com.senior.prompthub.domain.service.promptversion;

import br.com.senior.prompthub.config.security.SecurityUtils;
import br.com.senior.prompthub.core.repository.BaseRepository;
import br.com.senior.prompthub.core.service.AbstractBaseService;
import br.com.senior.prompthub.core.service.modelmapper.ModelMapperService;
import br.com.senior.prompthub.core.service.validate.CrudInterceptor;
import br.com.senior.prompthub.domain.entity.PromptVersion;
import br.com.senior.prompthub.domain.entity.User;
import br.com.senior.prompthub.domain.repository.PromptVersionRepository;
import br.com.senior.prompthub.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PromptVersionService extends AbstractBaseService<PromptVersion, Long> {

    private final PromptVersionRepository promptVersionRepository;
    private final ModelMapperService<PromptVersion> modelMapperService;
    private final UserRepository userRepository;

    @Override
    protected BaseRepository<PromptVersion, Long> getRepository() {
        return promptVersionRepository;
    }

    @Override
    protected ModelMapperService<PromptVersion> getModelMapperService() {
        return modelMapperService;
    }

    @Override
    protected CrudInterceptor<PromptVersion, Long> crudInterceptor() {
        return null;
    }

    @Override
    @Transactional
    public PromptVersion create(PromptVersion promptVersion) {
        promptVersion.setVersion(getNextVersion(promptVersion));
        promptVersion.setAuthor(getUser());
        return super.create(promptVersion);
    }

    private String getNextVersion(PromptVersion promptVersion) {
        return "V" + (promptVersionRepository.countByPromptId(promptVersion.getPrompt().getId()) + 1);
    }

    private static User getUser() {
        return User.builder().id(SecurityUtils.getCurrentUserId()).build();
    }

}
