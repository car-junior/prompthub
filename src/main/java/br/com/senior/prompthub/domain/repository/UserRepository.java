package br.com.senior.prompthub.domain.repository;

import br.com.senior.prompthub.core.repository.BaseRepository;
import br.com.senior.prompthub.domain.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {
    boolean existsByUsernameAndIdNot(String name, long id);
    
    boolean existsByEmailAndIdNot(String email, long id);
}
