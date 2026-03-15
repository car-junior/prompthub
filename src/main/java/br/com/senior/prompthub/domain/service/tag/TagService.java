package br.com.senior.prompthub.domain.service.tag;

import br.com.senior.prompthub.core.repository.BaseRepository;
import br.com.senior.prompthub.core.service.AbstractBaseService;
import br.com.senior.prompthub.core.service.modelmapper.ModelMapperService;
import br.com.senior.prompthub.core.service.validate.CrudInterceptor;
import br.com.senior.prompthub.domain.entity.Tag;
import br.com.senior.prompthub.domain.repository.TagRepository;
import br.com.senior.prompthub.infrastructure.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TagService extends AbstractBaseService<Tag, Long> {

    private final TagRepository tagRepository;
    private final ModelMapperService<Tag> modelMapperService;

    @Override
    protected BaseRepository<Tag, Long> getRepository() {
        return tagRepository;
    }

    @Override
    protected ModelMapperService<Tag> getModelMapperService() {
        return modelMapperService;
    }

    @Override
    protected CrudInterceptor<Tag, Long> crudInterceptor() {
        return null;
    }

    @Override
    @Transactional
    public Tag create(Tag tag) {
        validateUniqueness(tag);
        return super.create(tag);
    }

    @Override
    @Transactional
    public Tag update(Long id, Tag tag) {
        tag.setId(id);
        validateUniqueness(tag);
        return super.update(id, tag);
    }

    private void validateUniqueness(Tag tag) {
        Long id = tag.getId() != null ? tag.getId() : -1L;
        if (tagRepository.existsByNameAndIdNot(tag.getName(), id)) {
            throw CustomException.badRequest("Já existe uma tag com o nome: " + tag.getName());
        }
        if (tagRepository.existsBySlugAndIdNot(tag.getSlug(), id)) {
            throw CustomException.badRequest("Já existe uma tag com o slug: " + tag.getSlug());
        }
    }
}
