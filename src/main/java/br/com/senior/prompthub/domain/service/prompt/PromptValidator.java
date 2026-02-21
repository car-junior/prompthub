package br.com.senior.prompthub.domain.service.prompt;

import br.com.senior.prompthub.domain.entity.Prompt;
import br.com.senior.prompthub.domain.repository.PromptRepository;
import br.com.senior.prompthub.infrastructure.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PromptValidator {
    private final PromptRepository promptRepository;
    
    public void validatePrompt(Prompt prompt) {
        validatePromptType(prompt);
        validateUniqueName(prompt);
    }
    
    private void validatePromptType(Prompt prompt) {
        boolean isTeamPrompt = prompt.getTeam() != null && prompt.getOwner() == null;
        boolean isPersonalPrompt = prompt.getTeam() == null && prompt.getOwner() != null;
        
        if (!isTeamPrompt && !isPersonalPrompt) {
            throw CustomException.badRequest(
                "Prompt deve ser de equipe (teamId preenchido) OU pessoal (ownerId preenchido), não ambos ou nenhum"
            );
        }
    }
    
    private void validateUniqueName(Prompt prompt) {
        Long id = prompt.getId() != null ? prompt.getId() : 0L;
        
        if (prompt.getTeam() != null) {
            // Valida nome único dentro da equipe
            if (promptRepository.existsByTeamIdAndNameAndIdNot(prompt.getTeam().getId(), prompt.getName(), id)) {
                throw CustomException.badRequest("Já existe um prompt com este nome nesta equipe");
            }
        } else if (prompt.getOwner() != null) {
            // Valida nome único para o usuário
            if (promptRepository.existsByOwnerIdAndNameAndIdNot(prompt.getOwner().getId(), prompt.getName(), id)) {
                throw CustomException.badRequest("Você já possui um prompt com este nome");
            }
        }
    }
}
