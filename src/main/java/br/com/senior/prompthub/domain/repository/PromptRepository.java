package br.com.senior.prompthub.domain.repository;

import br.com.senior.prompthub.core.repository.BaseRepository;
import br.com.senior.prompthub.domain.entity.Prompt;
import org.springframework.stereotype.Repository;

@Repository
public interface PromptRepository extends BaseRepository<Prompt, Long> {

    boolean existsByTeamIdAndNameAndIdNot(Long teamId, String name, Long id);

    boolean existsByOwnerIdAndNameAndIdNot(Long ownerId, String name, Long id);
}
