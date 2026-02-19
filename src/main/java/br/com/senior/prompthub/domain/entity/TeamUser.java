package br.com.senior.prompthub.domain.entity;

import br.com.senior.prompthub.config.audit.Auditable;
import br.com.senior.prompthub.domain.enums.TeamRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@Table(name = "team_users", schema = "dbo")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@SequenceGenerator(name = "team_users_seq", schema = "dbo", allocationSize = 1)
public class TeamUser extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "team_users_seq")
    private long id;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private TeamRole role = TeamRole.VIEWER;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TeamUser teamUser = (TeamUser) o;
        return Objects.equals(id, teamUser.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
