package br.com.senior.prompthub.domain.spec.teamuser;

import br.com.senior.prompthub.domain.enums.EntityStatus;
import br.com.senior.prompthub.domain.enums.UserRole;

import java.util.List;

public record TeamUserSearch(String query, List<EntityStatus> status, List<UserRole> roles) {
}
