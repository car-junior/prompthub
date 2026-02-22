package br.com.senior.prompthub.domain.controller;

import br.com.senior.prompthub.core.controller.BaseCrudController;
import br.com.senior.prompthub.core.dto.PageParams;
import br.com.senior.prompthub.core.dto.PageResult;
import br.com.senior.prompthub.core.service.modelmapper.ModelMapperService;
import br.com.senior.prompthub.domain.dto.prompt.input.PromptInput;
import br.com.senior.prompthub.domain.dto.prompt.output.PromptOutput;
import br.com.senior.prompthub.domain.entity.Prompt;
import br.com.senior.prompthub.domain.service.prompt.PromptService;
import br.com.senior.prompthub.domain.spec.prompt.PromptSearch;
import br.com.senior.prompthub.domain.spec.prompt.PromptSpecification;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prompts")
public class PromptController {
    private final PromptService promptService;
    private final PromptSpecification promptSpecification;
    private final BaseCrudController<Prompt, Long> crudController;

    public PromptController(PromptService promptService,
                            ModelMapperService<Prompt> promptModelMapperService,
                            PromptSpecification promptSpecification) {
        this.promptService = promptService;
        this.promptSpecification = promptSpecification;
        this.crudController = new BaseCrudController<>(promptService, promptModelMapperService, Prompt.class);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PageResult<PromptOutput>> getAllPrompts(PageParams pageParams, PromptSearch search) {
        var prompts = promptService.getAllPrompts(pageParams, search);
        return ResponseEntity.ok(prompts);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@promptPermissionEvaluator.isAdmin()")
    public ResponseEntity<PromptOutput> getPromptById(@PathVariable Long id) {
        return crudController.getById(id).asDto(PromptOutput.class);
    }

    @PostMapping
    @PreAuthorize("@promptPermissionEvaluator.canCreate(#promptInput)")
    public ResponseEntity<PromptOutput> createPrompt(@Valid @RequestBody PromptInput promptInput) {
        return crudController.create(promptInput).asDto(PromptOutput.class);
    }

    @PutMapping("/{id}")
    @PreAuthorize("@promptPermissionEvaluator.canEdit(#id)")
    public ResponseEntity<PromptOutput> updatePrompt(@PathVariable Long id, @Valid @RequestBody PromptInput promptInput) {
        return crudController.update(id, promptInput).asDto(PromptOutput.class);
    }
}
