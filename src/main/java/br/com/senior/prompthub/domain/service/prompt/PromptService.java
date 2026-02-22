package br.com.senior.prompthub.domain.service.prompt;

import br.com.senior.prompthub.config.security.SecurityUtils;
import br.com.senior.prompthub.core.dto.PageParams;
import br.com.senior.prompthub.core.dto.PageResult;
import br.com.senior.prompthub.core.repository.BaseRepository;
import br.com.senior.prompthub.core.service.AbstractBaseService;
import br.com.senior.prompthub.core.service.modelmapper.ModelMapperService;
import br.com.senior.prompthub.core.service.validate.CrudInterceptor;
import br.com.senior.prompthub.domain.dto.prompt.output.PromptOutput;
import br.com.senior.prompthub.domain.entity.Prompt;
import br.com.senior.prompthub.domain.repository.PromptRepository;
import br.com.senior.prompthub.domain.repository.TeamUserRepository;
import br.com.senior.prompthub.domain.spec.prompt.PromptSearch;
import br.com.senior.prompthub.domain.spec.prompt.PromptSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PromptService extends AbstractBaseService<Prompt, Long> {
    private final PromptValidator promptValidator;
    private final PromptRepository promptRepository;
    private final TeamUserRepository teamUserRepository;
    private final PromptSpecification promptSpecification;
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

    @Transactional(readOnly = true)
    public PageResult<PromptOutput> getAllPrompts(PageParams pageParams, PromptSearch search) {
        var currentUserId = SecurityUtils.getCurrentUserId();
        Page<Prompt> result;

        if (!SecurityUtils.isAdmin()) {
            // Adiciona filtro de acessibilidade automaticamente
            List<Long> userTeamIds = getUserTeamIds(currentUserId);
            search.setOwnerId(currentUserId);
            search.setTeamsId(userTeamIds);
        }
        result = promptRepository.findAll(promptSpecification.getPredicate(search), pageParams.toPageable());

        return modelMapperService.toPage(PromptOutput.class, result);
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

    private List<Long> getUserTeamIds(Long userId) {
        return teamUserRepository.findAllByUserId(userId)
                .stream()
                .map(tu -> tu.getTeam().getId())
                .toList();
    }
}
