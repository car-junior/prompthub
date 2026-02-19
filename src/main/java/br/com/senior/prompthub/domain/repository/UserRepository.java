package br.com.senior.prompthub.domain.repository;

import br.com.senior.prompthub.core.repository.BaseRepository;
import br.com.senior.prompthub.domain.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {
    boolean existsByUsernameAndIdNot(String name, long id);
    
    boolean existsByEmailAndIdNot(String email, long id);

    @Query("SELECT u FROM User u WHERE u.username = :username OR u.email = :username")
    Optional<User> findByUsernameOrEmail(String username);
}
