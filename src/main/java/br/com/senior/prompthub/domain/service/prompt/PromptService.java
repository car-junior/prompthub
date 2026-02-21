package br.com.senior.prompthub.domain.service.prompt;

import br.com.senior.prompthub.core.repository.BaseRepository;
import br.com.senior.prompthub.core.service.AbstractBaseService;
import br.com.senior.prompthub.core.service.modelmapper.ModelMapperService;
import br.com.senior.prompthub.core.service.validate.CrudInterceptor;
import br.com.senior.prompthub.domain.entity.Prompt;
import br.com.senior.prompthub.domain.repository.PromptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PromptService extends AbstractBaseService<Prompt, Long> {
    private final PromptRepository promptRepository;
    private final PromptValidator promptValidator;
    private final ModelMapperService<Prompt> modelMapperService;

    @Override
    protected BaseRepository<Prompt, Long> getRepository() {
        return promptRepository;
    }

    @Override
    protected ModelMapperService<Prompt> getModelMapperService() {
        return modelMapperService;
    }

    @Override
    protected CrudInterceptor<Prompt, Long> crudInterceptor() {
        return null;
    }

    @Override
    @Transactional
    public Prompt create(Prompt prompt) {
        promptValidator.validatePrompt(prompt);
        return super.create(prompt);
    }

    @Override
    @Transactional
    public Prompt update(Long id, Prompt prompt) {
        prompt.setId(id);
        promptValidator.validatePrompt(prompt);
        return super.update(id, prompt);
    }
}
