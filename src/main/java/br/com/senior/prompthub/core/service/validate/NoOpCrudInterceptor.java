package br.com.senior.prompthub.core.service.validate;

public class NoOpCrudInterceptor<T, ID> implements CrudInterceptor<T, ID> {
    @Override
    public void beforeCreate(T entity) {
    }

    @Override
    public void beforeUpdate(ID id, T entity) {

    }

    @Override
    public void beforeDelete(ID id) {

    }
}