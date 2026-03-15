package br.com.senior.prompthub.domain.spec.tag;

import br.com.senior.prompthub.core.specification.BaseSpecification;
import br.com.senior.prompthub.domain.entity.Tag;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static br.com.senior.prompthub.utils.CriteriaUtils.toLikeValue;
import static br.com.senior.prompthub.utils.CriteriaUtils.toNormalize;
import static br.com.senior.prompthub.utils.GeneralUtils.isPresent;

@Component
public class TagSpecification implements BaseSpecification<Tag, TagSearch> {

    @Override
    public Specification<Tag> getPredicate(TagSearch search) {
        return (root, query, builder) -> {
            final List<Predicate> predicates = new ArrayList<>();

            if (isPresent(search.getQuery())) {
                predicates.add(
                        builder.or(
                                builder.like(toNormalize(builder, root.get("name")), toLikeValue(search.getQuery())),
                                builder.like(toNormalize(builder, root.get("slug")), toLikeValue(search.getQuery()))
                        )
                );
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
