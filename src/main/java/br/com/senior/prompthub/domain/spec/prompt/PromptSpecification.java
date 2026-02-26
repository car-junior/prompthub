package br.com.senior.prompthub.domain.spec.prompt;

import br.com.senior.prompthub.core.specification.BaseSpecification;
import br.com.senior.prompthub.domain.entity.Prompt;
import br.com.senior.prompthub.domain.entity.Prompt_;
import br.com.senior.prompthub.domain.entity.User_;
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
public class PromptSpecification implements BaseSpecification<Prompt, PromptSearch> {

    @Override
    public Specification<Prompt> getPredicate(PromptSearch search) {
        return (root, query, builder) -> {
            final List<Predicate> predicates = new ArrayList<>();

            if (isPresent(search.getQuery())) {
                predicates.add(
                        builder.or(
                                builder.like(toNormalize(builder, root.get(Prompt_.NAME)), toLikeValue(search.getQuery())),
                                builder.like(toNormalize(builder, root.get(Prompt_.DESCRIPTION)), toLikeValue(search.getQuery()))
                        )
                );
            }

            List<Predicate> orPredicates = new ArrayList<>();

            if (isPresent(search.getUserId())) {
                orPredicates.add(builder.equal(root.get(Prompt_.OWNER).get(User_.ID), search.getUserId()));
            }

            if (isNotEmpty(search.getTeamsIds())) {
                orPredicates.add(root.get(Prompt_.TEAM).get(User_.ID).in(search.getTeamsIds()));
            }

            if (!orPredicates.isEmpty()) {
                predicates.add(builder.or(orPredicates.toArray(new Predicate[0])));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
