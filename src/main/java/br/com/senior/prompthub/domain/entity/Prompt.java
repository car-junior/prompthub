package br.com.senior.prompthub.domain.entity;

import br.com.senior.prompthub.config.audit.Auditable;
import br.com.senior.prompthub.core.service.modelmapper.NoUpdateMapping;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@Table(name = "prompts", schema = "dbo")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@SequenceGenerator(name = "prompts_seq", schema = "dbo", allocationSize = 1)
public class Prompt extends Auditable {
    @Id
    @NoUpdateMapping
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prompts_seq")
    private Long id;

    @ManyToOne
    @NoUpdateMapping
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne
    @NoUpdateMapping
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Prompt prompt = (Prompt) o;
        return Objects.equals(id, prompt.id) && Objects.equals(name, prompt.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
