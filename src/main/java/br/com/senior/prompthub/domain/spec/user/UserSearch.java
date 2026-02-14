package br.com.senior.prompthub.domain.spec.user;

import br.com.senior.prompthub.domain.enums.EntityStatus;

import java.util.List;

public record UserSearch(String query, List<EntityStatus> status) {
}
