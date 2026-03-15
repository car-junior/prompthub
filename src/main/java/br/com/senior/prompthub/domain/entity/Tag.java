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
@Table(name = "tags", schema = "dbo")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@SequenceGenerator(name = "tags_seq", schema = "dbo", allocationSize = 1)
public class Tag extends Auditable {

    @Id
    @NoUpdateMapping
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tags_seq")
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;

    @Column(name = "slug", nullable = false, unique = true, length = 50)
    private String slug;

    @Column(name = "description", length = 255)
    private String description;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(id, tag.id) && Objects.equals(slug, tag.slug);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, slug);
    }
}
