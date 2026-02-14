package br.com.senior.prompthub.domain.spec.team;

import br.com.senior.prompthub.domain.enums.EntityStatus;

import java.util.List;

public record TeamSearch(String query, List<EntityStatus> status) {
}
