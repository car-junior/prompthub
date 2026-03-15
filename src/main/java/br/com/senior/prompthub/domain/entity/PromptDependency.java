package br.com.senior.prompthub.domain.entity;

import br.com.senior.prompthub.config.audit.Auditable;
import br.com.senior.prompthub.core.service.modelmapper.NoUpdateMapping;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

/**
 * Dependências entre prompts.
 * Entidade mapeada para uso futuro — sem endpoints expostos por enquanto.
 */
@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@Table(name = "prompt_dependencies", schema = "dbo")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@SequenceGenerator(name = "prompt_dependencies_seq", schema = "dbo", allocationSize = 1)
public class PromptDependency extends Auditable {

    @Id
    @NoUpdateMapping
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prompt_dependencies_seq")
    private Long id;

    @ManyToOne
    @NoUpdateMapping
    @JoinColumn(name = "prompt_id", nullable = false)
    private Prompt prompt;

    @ManyToOne
    @NoUpdateMapping
    @JoinColumn(name = "depends_on_prompt_id", nullable = false)
    private Prompt dependsOnPrompt;

    @Column(name = "min_version", length = 50)
    private String minVersion;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PromptDependency that = (PromptDependency) o;
        return Objects.equals(id, that.id)
                && Objects.equals(prompt, that.prompt)
                && Objects.equals(dependsOnPrompt, that.dependsOnPrompt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, prompt, dependsOnPrompt);
    }
}
