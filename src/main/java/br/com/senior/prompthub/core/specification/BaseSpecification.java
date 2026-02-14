package br.com.senior.prompthub.core.specification;

import org.springframework.data.jpa.domain.Specification;

public interface BaseSpecification<E, S> {
    Specification<E> getPredicate(S search);
}
