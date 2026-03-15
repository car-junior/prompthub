package br.com.senior.prompthub.domain.controller;

import br.com.senior.prompthub.core.controller.BaseCrudController;
import br.com.senior.prompthub.core.dto.PageParams;
import br.com.senior.prompthub.core.dto.PageResult;
import br.com.senior.prompthub.core.service.modelmapper.ModelMapperService;
import br.com.senior.prompthub.domain.dto.promptversion.input.PromptVersionInput;
import br.com.senior.prompthub.domain.dto.promptversion.output.PromptVersionOutput;
import br.com.senior.prompthub.domain.entity.PromptVersion;
import br.com.senior.prompthub.domain.enums.PromptVersionStatus;
import br.com.senior.prompthub.domain.enums.PromptVersionVisibility;
import br.com.senior.prompthub.domain.service.promptversion.PromptVersionService;
import br.com.senior.prompthub.domain.spec.promptversion.PromptVersionSearch;
import br.com.senior.prompthub.domain.spec.promptversion.PromptVersionSpecification;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prompt-versions")
public class PromptVersionController {

    private final PromptVersionService promptVersionService;
    private final PromptVersionSpecification promptVersionSpecification;
    private final BaseCrudController<PromptVersion, Long> crudController;

    public PromptVersionController(PromptVersionService promptVersionService,
                                   ModelMapperService<PromptVersion> promptVersionModelMapperService,
                                   PromptVersionSpecification promptVersionSpecification) {
        this.promptVersionService = promptVersionService;
        this.promptVersionSpecification = promptVersionSpecification;
        this.crudController = new BaseCrudController<>(promptVersionService, promptVersionModelMapperService, PromptVersion.class);
    }

    @GetMapping
    @PreAuthorize("@promptVersionPermissionEvaluator.canList()")
    public ResponseEntity<PageResult<PromptVersionOutput>> getAllVersions(PageParams pageParams, PromptVersionSearch search) {
        return crudController.getAllSpec(pageParams, promptVersionSpecification, search).asPageDto(PromptVersionOutput.class);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@promptVersionPermissionEvaluator.canView(#id)")
    public ResponseEntity<PromptVersionOutput> getVersionById(@PathVariable Long id) {
        return crudController.getById(id).asDto(PromptVersionOutput.class);
    }

    @PostMapping
    @PreAuthorize("@promptVersionPermissionEvaluator.canCreate(#input)")
    public ResponseEntity<PromptVersionOutput> createVersion(@Valid @RequestBody PromptVersionInput input) {
        return crudController.create(input).asDto(PromptVersionOutput.class);
    }

    @PutMapping("/{id}")
    @PreAuthorize("@promptVersionPermissionEvaluator.canEdit(#id)")
    public ResponseEntity<PromptVersionOutput> updateVersion(@PathVariable Long id, @Valid @RequestBody PromptVersionInput input) {
        return crudController.update(id, input).asDto(PromptVersionOutput.class);
    }

    @PatchMapping("/{id}/change-status")
    @PreAuthorize("@promptVersionPermissionEvaluator.canEdit(#id)")
    public ResponseEntity<Void> changeStatus(@PathVariable Long id, @RequestParam PromptVersionStatus status) {
        promptVersionService.changeStatus(id, status);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/change-visibility")
    @PreAuthorize("@promptVersionPermissionEvaluator.canEdit(#id)")
    public ResponseEntity<Void> changeVisibility(@PathVariable Long id, @RequestParam PromptVersionVisibility visibility) {
        promptVersionService.changeVisibility(id, visibility);
        return ResponseEntity.noContent().build();
    }
}
