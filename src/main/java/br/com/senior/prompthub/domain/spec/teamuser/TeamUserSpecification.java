package br.com.senior.prompthub.domain.spec.teamuser;

import br.com.senior.prompthub.domain.entity.*;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static br.com.senior.prompthub.utils.CriteriaUtils.formatQueryToLike;
import static br.com.senior.prompthub.utils.CriteriaUtils.lowerAndUnaccented;
import static br.com.senior.prompthub.utils.GeneralUtils.isNotEmpty;
import static br.com.senior.prompthub.utils.GeneralUtils.isPresent;

@Component
public class TeamUserSpecification {
    public Specification<TeamUser> getPredicate(Long teamId, TeamUserSearch teamUserSearch) {
        return (root, query, builder) -> {
            final List<Predicate> predicates = new ArrayList<>();

            predicates.add(builder.equal(root.get(TeamUser_.TEAM).get(Team_.ID), teamId));

            if (isNotEmpty(teamUserSearch.status())) {
                predicates.add(root.get(TeamUser_.USER).get(User_.STATUS).in(teamUserSearch.status()));
            }

            if (isNotEmpty(teamUserSearch.roles())) {
                predicates.add(root.get(TeamUser_.ROLE).in(teamUserSearch.roles()));
            }

            if (isPresent(teamUserSearch.query())) {
                predicates.add(
                        builder.or(
                                builder.like(lowerAndUnaccented(builder, root.get(TeamUser_.USER).get(User_.USERNAME)), formatQueryToLike(teamUserSearch.query())),
                                builder.like(lowerAndUnaccented(builder, root.get(TeamUser_.USER).get(User_.EMAIL)), formatQueryToLike(teamUserSearch.query()))
                        )
                );
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
