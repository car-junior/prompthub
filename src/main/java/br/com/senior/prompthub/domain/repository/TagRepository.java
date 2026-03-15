package br.com.senior.prompthub.domain.repository;

import br.com.senior.prompthub.core.repository.BaseRepository;
import br.com.senior.prompthub.domain.entity.Tag;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends BaseRepository<Tag, Long> {

    boolean existsBySlugAndIdNot(String slug, Long id);

    boolean existsByNameAndIdNot(String name, Long id);
}
