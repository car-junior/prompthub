package br.com.senior.prompthub.domain.spec.prompt;

import br.com.senior.prompthub.core.specification.BaseSpecification;
import br.com.senior.prompthub.domain.entity.Prompt;
import br.com.senior.prompthub.domain.entity.Prompt_;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static br.com.senior.prompthub.utils.CriteriaUtils.toLikeValue;
import static br.com.senior.prompthub.utils.CriteriaUtils.toNormalize;
import static br.com.senior.prompthub.utils.GeneralUtils.isPresent;

@Component
public class PromptSpecification implements BaseSpecification<Prompt, PromptSearch> {
    
    @Override
    public Specification<Prompt> getPredicate(PromptSearch search) {
        return (root, query, builder) -> {
            final List<Predicate> predicates = new ArrayList<>();

            if (isPresent(search.getName())) {
                predicates.add(
                    builder.like(
                        toNormalize(builder, root.get(Prompt_.NAME)), 
                        toLikeValue(search.getName())
                    )
                );
            }

//            if (isPresent(search.getTeamId())) {
//                predicates.add(builder.equal(root.get(Prompt_.TEAM_ID), search.getTeamId()));
//            }
//
//            if (isPresent(search.getOwnerId())) {
//                predicates.add(builder.equal(root.get(Prompt_.OWNER_ID), search.getOwnerId()));
//            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
