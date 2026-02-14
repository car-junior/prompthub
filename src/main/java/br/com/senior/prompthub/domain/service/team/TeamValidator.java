package br.com.senior.prompthub.domain.service.team;

import br.com.senior.prompthub.domain.repository.TeamRepository;
import br.com.senior.prompthub.infrastructure.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TeamValidator {
    private final TeamRepository teamRepository;

    public void validateTeamNameUniqueness(String name, long id) {
        if (teamRepository.existsByNameAndIdNot(name, id)) {
            throw CustomException.badRequest("Já existe um time com o nome: " + name);
        }
    }
}
