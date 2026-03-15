package br.com.senior.prompthub.domain.dto.promptversion.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromptVersionOutput {
    private Long id;
    private String version;
    private String content;
    private String changeNotes;
    private PromptOutput prompt;
    private UserOutput author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
