package br.com.senior.prompthub.domain.dto.prompt.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromptOutput {
    private Long id;
    private String name;
    private String description;
    private UserOutput owner;
    private TeamOutput team;
    private List<TagOutput> tags;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
