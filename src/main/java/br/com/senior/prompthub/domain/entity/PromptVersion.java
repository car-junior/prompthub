package br.com.senior.prompthub.domain.entity;

import br.com.senior.prompthub.config.audit.Auditable;
import br.com.senior.prompthub.core.service.modelmapper.NoUpdateMapping;
import br.com.senior.prompthub.domain.enums.PromptVersionStatus;
import br.com.senior.prompthub.domain.enums.PromptVersionVisibility;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.util.Objects;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@SQLRestriction("status <> 'DELETED'")
@Table(name = "prompt_versions", schema = "dbo")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@SequenceGenerator(name = "prompt_versions_seq", schema = "dbo", allocationSize = 1)
public class PromptVersion extends Auditable {

    @Id
    @NoUpdateMapping
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prompt_versions_seq")
    private Long id;

    @ManyToOne
    @NoUpdateMapping
    @JoinColumn(name = "prompt_id", nullable = false)
    private Prompt prompt;

    @ManyToOne
    @NoUpdateMapping
    @JoinColumn(name = "author_id")
    private User author;

    @Column(name = "version", nullable = false, length = 3)
    private String version;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PromptVersionStatus status = PromptVersionStatus.DRAFT;

    @Enumerated(EnumType.STRING)
    @Column(name = "visibility", nullable = false)
    private PromptVersionVisibility visibility = PromptVersionVisibility.PRIVATE;

    @Column(name = "change_notes", columnDefinition = "TEXT")
    private String changeNotes;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PromptVersion that = (PromptVersion) o;
        return Objects.equals(id, that.id) && Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version);
    }
}
