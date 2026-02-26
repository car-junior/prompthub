package br.com.senior.prompthub.domain.spec.team;

import br.com.senior.prompthub.domain.entity.Team;
import br.com.senior.prompthub.domain.entity.TeamUser_;
import br.com.senior.prompthub.domain.entity.Team_;
import br.com.senior.prompthub.core.specification.BaseSpecification;
import br.com.senior.prompthub.domain.entity.User_;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static br.com.senior.prompthub.utils.CriteriaUtils.toLikeValue;
import static br.com.senior.prompthub.utils.CriteriaUtils.toNormalize;
import static br.com.senior.prompthub.utils.GeneralUtils.isNotEmpty;
import static br.com.senior.prompthub.utils.GeneralUtils.isPresent;

@Component
public class TeamSpecification implements BaseSpecification<Team, TeamSearch> {
    public Specification<Team> getPredicate(TeamSearch teamSearch) {
        return (root, query, builder) -> {
            final List<Predicate> predicates = new ArrayList<>();

            if (isPresent(teamSearch.getStatus())) {
                predicates.add(root.get(User_.STATUS).in(teamSearch.getStatus()));
            }

            var orPredicates = new ArrayList<Predicate>();

            if (isPresent(teamSearch.getUserId())) {
                var teamUserJoin = root.join(Team_.members, JoinType.LEFT);
                orPredicates.add(builder.equal(teamUserJoin.get(TeamUser_.USER).get(User_.ID), teamSearch.getUserId()));
            }

            if (isNotEmpty(teamSearch.getUserTeamsIds())) {
                var teamUserJoin = root.join(Team_.members, JoinType.LEFT);
                orPredicates.add(teamUserJoin.get(TeamUser_.TEAM).get(Team_.ID).in(teamSearch.getUserTeamsIds()));
            }

            if (isPresent(teamSearch.getQuery())) {
                predicates.add(
                        builder.or(
                                builder.like(toNormalize(builder, root.get(Team_.NAME)), toLikeValue(teamSearch.getQuery()))
                        )
                );
            }

            if (isNotEmpty(orPredicates)) {
                predicates.add(builder.or(orPredicates.toArray(new Predicate[0])));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
