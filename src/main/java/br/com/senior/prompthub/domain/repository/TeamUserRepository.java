package br.com.senior.prompthub.domain.repository;

import br.com.senior.prompthub.core.repository.BaseRepository;
import br.com.senior.prompthub.domain.entity.TeamUser;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamUserRepository extends BaseRepository<TeamUser, Long> {
    Optional<TeamUser> findByTeamIdAndUserId(Long teamUser, Long userId);
}
