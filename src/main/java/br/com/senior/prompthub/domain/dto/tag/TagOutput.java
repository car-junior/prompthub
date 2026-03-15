package br.com.senior.prompthub.domain.dto.tag;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagOutput {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
