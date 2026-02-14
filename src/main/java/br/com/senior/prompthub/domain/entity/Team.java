package br.com.senior.prompthub.domain.entity;

import br.com.senior.prompthub.config.audit.Auditable;
import br.com.senior.prompthub.core.service.modelmapper.NoUpdateMapping;
import br.com.senior.prompthub.domain.enums.EntityStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@Table(name = "teams", schema = "dbo")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@SequenceGenerator(name = "teams_seq", schema = "dbo", allocationSize = 1)
public class Team extends Auditable {
    @Id
    @NoUpdateMapping
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teams_seq")
    private long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EntityStatus status = EntityStatus.ACTIVE;

    @NoUpdateMapping
    @Builder.Default
    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    private List<TeamUser> members = new ArrayList<>();

    public List<TeamUser> cloneMembers() {
        return new ArrayList<>(members);
    }

    public void addAllMembers(List<TeamUser> teamUsers) {
        members.addAll(teamUsers);
    }

    public void clearMembers() {
        members.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(id, team.id) && Objects.equals(name, team.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
