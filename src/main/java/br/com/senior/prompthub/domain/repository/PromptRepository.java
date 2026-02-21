package br.com.senior.prompthub.domain.repository;

import br.com.senior.prompthub.core.repository.BaseRepository;
import br.com.senior.prompthub.domain.entity.Prompt;
import br.com.senior.prompthub.domain.enums.TeamRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.EnumSet;

@Repository
public interface PromptRepository extends BaseRepository<Prompt, Long> {

    boolean existsByTeamIdAndNameAndIdNot(Long teamId, String name, Long id);

    boolean existsByOwnerIdAndNameAndIdNot(Long ownerId, String name, Long id);

    @Query("""            
            SELECT COUNT(p) > 0  FROM Prompt p
                WHERE p.id = :promptId
                AND EXISTS (
                    SELECT 1 FROM TeamUser tu
                    WHERE tu.team.id = p.team.id
                    AND tu.user.id = :userId
                    AND tu.role IN (:roles)
                )
            """)
    boolean isTeamManagerOfPrompt(@Param("promptId") Long promptId,
                                  @Param("userId") Long userId,
                                  @Param("roles") EnumSet<TeamRole> roles);
}
