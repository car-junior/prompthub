package br.com.senior.prompthub.domain.entity;

import br.com.senior.prompthub.config.audit.Auditable;
import br.com.senior.prompthub.core.service.modelmapper.NoUpdateMapping;
import br.com.senior.prompthub.domain.enums.EntityStatus;
import br.com.senior.prompthub.domain.enums.GlobalRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@SQLRestriction("status <> 'DELETED'")
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

    @NoUpdateMapping
    @Column(name = "password")
    private String password;

    @Transient
    private String tempPassword;

    @NoUpdateMapping
    @Column(name = "must_change_password", nullable = false)
    private Boolean mustChangePassword = false;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private GlobalRole role = GlobalRole.USER;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EntityStatus status = EntityStatus.ACTIVE;

    //TODO: NAO FAZ SENTIDO CRIAR TEMS PARA USUÁRIO, DEVERIA SER O CONTRÁRIO, VER SE DEIXA ASSIM MESMO OU SE ALTERA A MODELAGEM
    @NoUpdateMapping
    @Builder.Default
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<TeamUser> teams = new ArrayList<>();

    public Collection<GrantedAuthority> getAuthorities() {
        var authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
        authorities.addAll(getAuthoritiesTeamUser());
        return authorities;
    }

    private List<SimpleGrantedAuthority> getAuthoritiesTeamUser() {
        return teams.stream().map(it -> new SimpleGrantedAuthority("ROLE_" + it.getRole())).toList();
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
