package br.com.senior.prompthub.domain.spec.team;

import br.com.senior.prompthub.domain.entity.Team;
import br.com.senior.prompthub.domain.entity.Team_;
import br.com.senior.prompthub.core.specification.BaseSpecification;
import br.com.senior.prompthub.domain.entity.User_;
import br.com.senior.prompthub.domain.enums.EntityStatus;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static br.com.senior.prompthub.utils.CriteriaUtils.formatQueryToLike;
import static br.com.senior.prompthub.utils.CriteriaUtils.lowerAndUnaccented;
import static br.com.senior.prompthub.utils.GeneralUtils.isPresent;

@Component
public class TeamSpecification implements BaseSpecification<Team, TeamSearch> {
    public Specification<Team> getPredicate(TeamSearch teamSearch) {
        return (root, query, builder) -> {
            final List<Predicate> predicates = new ArrayList<>();

            if (isPresent(teamSearch.status())) {
                predicates.add(root.get(User_.STATUS).in(teamSearch.status()));
            }

            if (isPresent(teamSearch.query())) {
                predicates.add(
                        builder.or(
                                builder.like(lowerAndUnaccented(builder, root.get(Team_.NAME)), formatQueryToLike(teamSearch.query()))
                        )
                );
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
