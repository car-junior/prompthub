package br.com.senior.prompthub.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface BaseService<T, ID> {
    T create(T entity);

    List<T> createAll(Iterable<T> entities);

    T update(ID id, T entity);

    void deleteById(ID id);

    boolean existsById(ID id);

    T getById(ID id);

    List<T> getAll();

    Page<T> getAll(Pageable pageable);

    Page<T> getAll(Specification<T> spec, Pageable pageable);

}
