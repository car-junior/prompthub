package br.com.senior.prompthub.domain.entity;

import br.com.senior.prompthub.config.audit.Auditable;
import br.com.senior.prompthub.core.service.modelmapper.NoUpdateMapping;
import br.com.senior.prompthub.domain.enums.EntityStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@Table(name = "users", schema = "dbo")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@SequenceGenerator(name = "users_seq", schema = "dbo", allocationSize = 1)
public class User extends Auditable {
    @Id
    @NoUpdateMapping
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
    private long id;

    @Column(name = "username", nullable = false, unique = true, length = 100)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EntityStatus status = EntityStatus.ACTIVE;

    @NoUpdateMapping
    @Builder.Default
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<TeamUser> teams = new ArrayList<>();


    public List<TeamUser> cloneTeams() {
        return new ArrayList<>(teams);
    }

    public void addAllTeams(List<TeamUser> teamUsers) {
        teams.addAll(teamUsers);
    }

    public void clearTeams() {
        teams.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }
}
