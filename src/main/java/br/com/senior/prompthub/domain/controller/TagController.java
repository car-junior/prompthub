package br.com.senior.prompthub.domain.controller;

import br.com.senior.prompthub.core.controller.BaseCrudController;
import br.com.senior.prompthub.core.dto.PageParams;
import br.com.senior.prompthub.core.dto.PageResult;
import br.com.senior.prompthub.core.service.modelmapper.ModelMapperService;
import br.com.senior.prompthub.domain.dto.tag.TagInput;
import br.com.senior.prompthub.domain.dto.tag.TagOutput;
import br.com.senior.prompthub.domain.entity.Tag;
import br.com.senior.prompthub.domain.service.tag.TagService;
import br.com.senior.prompthub.domain.spec.tag.TagSearch;
import br.com.senior.prompthub.domain.spec.tag.TagSpecification;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagSpecification tagSpecification;
    private final BaseCrudController<Tag, Long> crudController;

    public TagController(TagService tagService,
                         ModelMapperService<Tag> tagModelMapperService,
                         TagSpecification tagSpecification) {
        this.tagSpecification = tagSpecification;
        this.crudController = new BaseCrudController<>(tagService, tagModelMapperService, Tag.class);
    }

    @GetMapping
    @PreAuthorize("@tagPermissionEvaluator.canList()")
    public ResponseEntity<PageResult<TagOutput>> getAllTags(PageParams pageParams, TagSearch search) {
        return crudController.getAllSpec(pageParams, tagSpecification, search).asPageDto(TagOutput.class);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@tagPermissionEvaluator.canView(#id)")
    public ResponseEntity<TagOutput> getTagById(@PathVariable Long id) {
        return crudController.getById(id).asDto(TagOutput.class);
    }

    @PostMapping
    @PreAuthorize("@tagPermissionEvaluator.canCreate(#tagInput)")
    public ResponseEntity<TagOutput> createTag(@Valid @RequestBody TagInput tagInput) {
        return crudController.create(tagInput).asDto(TagOutput.class);
    }

    @PutMapping("/{id}")
    @PreAuthorize("@tagPermissionEvaluator.canEdit(#id)")
    public ResponseEntity<TagOutput> updateTag(@PathVariable Long id, @Valid @RequestBody TagInput tagInput) {
        return crudController.update(id, tagInput).asDto(TagOutput.class);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@tagPermissionEvaluator.canDelete(#id)")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        return crudController.delete(id);
    }
}
