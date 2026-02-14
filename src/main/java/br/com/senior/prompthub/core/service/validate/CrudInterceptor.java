package br.com.senior.prompthub.core.service.validate;

public interface CrudInterceptor<T, ID> {
    void beforeCreate(T entity);

    void beforeUpdate(ID id, T entity);

    void beforeDelete(ID id);

    default void beforeCreateAll(Iterable<T> entities) {}
    default void afterCreate(T entity) {}
    default void afterUpdate(ID id, T entity) {}
    default void afterDelete(ID id) {}
    default void afterCreateAll(Iterable<T> entities) {}
}
