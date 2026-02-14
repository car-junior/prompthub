package br.com.senior.prompthub.domain.spec.user;

import br.com.senior.prompthub.core.specification.BaseSpecification;
import br.com.senior.prompthub.domain.entity.User;
import br.com.senior.prompthub.domain.entity.User_;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static br.com.senior.prompthub.utils.CriteriaUtils.formatQueryToLike;
import static br.com.senior.prompthub.utils.CriteriaUtils.lowerAndUnaccented;
import static br.com.senior.prompthub.utils.GeneralUtils.isPresent;

@Component
public class UserSpecification implements BaseSpecification<User, UserSearch> {
    public Specification<User> getPredicate(UserSearch userSearch) {
        return (root, query, builder) -> {
            final List<Predicate> predicates = new ArrayList<>();

            if (isPresent(userSearch.active())) {
                predicates.add(builder.equal(root.get(User_.IS_ACTIVE), userSearch.active()));
            }

            if (isPresent(userSearch.query())) {
                predicates.add(
                        builder.or(
                                builder.like(lowerAndUnaccented(builder, root.get(User_.USERNAME)), formatQueryToLike(userSearch.query())),
                                builder.like(lowerAndUnaccented(builder, root.get(User_.EMAIL)), formatQueryToLike(userSearch.query()))
                        )
                );
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
