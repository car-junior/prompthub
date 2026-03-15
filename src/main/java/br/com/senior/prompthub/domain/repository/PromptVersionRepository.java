package br.com.senior.prompthub.domain.repository;

import br.com.senior.prompthub.core.repository.BaseRepository;
import br.com.senior.prompthub.domain.entity.PromptVersion;
import org.springframework.stereotype.Repository;

@Repository
public interface PromptVersionRepository extends BaseRepository<PromptVersion, Long> {
    int countByPromptId(Long promptId);
}
