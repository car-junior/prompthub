package br.com.senior.prompthub.domain.repository;

import br.com.senior.prompthub.core.repository.BaseRepository;
import br.com.senior.prompthub.domain.entity.User;
import br.com.senior.prompthub.domain.enums.TeamRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.EnumSet;
import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {
    boolean existsByUsernameAndIdNot(String name, long id);
    
    boolean existsByEmailAndIdNot(String email, long id);

    @Query("SELECT u FROM User u WHERE (u.username = :username OR u.email = :username) and u.status = 'ACTIVE'")
    Optional<User> findByUsernameOrEmail(String username);


    @Query("""
                 select COUNT(u) > 0 from User u join TeamUser tu on tu.user.id = u.id
                 where u.id = :userId and tu.team.id = :teamId and tu.role IN (:roles)
            """)
    boolean userHasTeamRole(@Param("userId") long userId,
                            @Param("teamId") long teamId,
                            @Param("roles") EnumSet<TeamRole> roles);
}
