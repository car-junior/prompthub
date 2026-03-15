package br.com.senior.prompthub.domain.entity;

import br.com.senior.prompthub.config.audit.Auditable;
import br.com.senior.prompthub.core.service.modelmapper.NoUpdateMapping;
import br.com.senior.prompthub.domain.enums.PromptVersionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

/**
 * Histórico de mudanças de status de versões de prompts.
 * Entidade mapeada para uso futuro — sem endpoints expostos por enquanto.
 */
@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@Table(name = "prompt_history", schema = "dbo")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@SequenceGenerator(name = "prompt_history_seq", schema = "dbo", allocationSize = 1)
public class PromptHistory extends Auditable {
    @Id
    @NoUpdateMapping
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prompt_history_seq")
    private long id;

    @ManyToOne
    @NoUpdateMapping
    @JoinColumn(name = "prompt_version_id", nullable = false)
    private PromptVersion promptVersion;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @Enumerated(EnumType.STRING)
    @Column(name = "old_status", length = 30)
    private PromptVersionStatus oldStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "new_status", nullable = false, length = 30)
    private PromptVersionStatus newStatus;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PromptHistory that = (PromptHistory) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
