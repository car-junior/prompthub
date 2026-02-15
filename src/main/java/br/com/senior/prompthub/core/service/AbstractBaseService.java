package br.com.senior.prompthub.core.service;

import br.com.senior.prompthub.core.repository.BaseRepository;
import br.com.senior.prompthub.core.service.modelmapper.ModelMapperService;
import br.com.senior.prompthub.core.service.validate.CrudInterceptor;
import br.com.senior.prompthub.infrastructure.exception.CustomException;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.util.List;

@Getter
public abstract class AbstractBaseService<T, ID> implements BaseService<T, ID> {
    private static final String MESSAGE_NOT_FOUND = "%s não encontrado com ID: %s";

    protected abstract BaseRepository<T, ID> getRepository();

    protected abstract ModelMapperService<T> getModelMapperService();

    protected abstract CrudInterceptor<T, ID> crudInterceptor();

    private final Class<T> entityClass;

    @SuppressWarnings("unchecked")
    public AbstractBaseService() {
        // Pega o tipo genérico T em tempo de execução
        var type = getClass().getGenericSuperclass();
        var paramType = (ParameterizedType) type;
        this.entityClass = (Class<T>) paramType.getActualTypeArguments()[0];
    }


    @Override
    @Transactional
    public T create(T entity) {
        if (crudInterceptor() != null) {
            crudInterceptor().beforeCreate(entity);
        }
        entity = saveEntity(entity);
        if (crudInterceptor() != null) {
            crudInterceptor().afterCreate(entity);
        }
        return entity;
    }

    @Override
    @Transactional
    public List<T> createAll(Iterable<T> entities) {
        if (crudInterceptor() != null) {
            crudInterceptor().beforeCreateAll(entities);
        }
        entities = getRepository().saveAll(entities);
        if (crudInterceptor() != null) {
            crudInterceptor().afterCreateAll(entities);
        }
        return (List<T>) entities;
    }

    @Override
    @Transactional
    public T update(ID id, T entityUpdate) {
        var entity = getById(id);
        if (crudInterceptor() != null) {
            crudInterceptor().beforeUpdate(id, entityUpdate);
        }
        getModelMapperService().updateEntity(entityUpdate, entity);
        entity = saveEntity(entity);
        if (crudInterceptor() != null) {
            crudInterceptor().afterUpdate(id, entity);
        }
        return entity;
    }

    @Override
    @Transactional
    public void deleteById(ID id) {
        if (!getRepository().existsById(id)) throw getMessageNotFound(id);
        if (crudInterceptor() != null) {
            crudInterceptor().beforeDelete(id);
        }
        getRepository().deleteById(id);
        if (crudInterceptor() != null) {
            crudInterceptor().afterDelete(id);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public T getById(ID id) {
        return getRepository().findById(id).orElseThrow(() -> getMessageNotFound(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> getAll() {
        return getRepository().findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<T> getAll(Pageable pageable) {
        return getRepository().findAll(pageable);
    }

    @Override
    public Page<T> getAll(Specification<T> spec, Pageable pageable) {
        return getRepository().findAll(spec, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(ID id) {
        return getRepository().existsById(id);
    }

    private CustomException getMessageNotFound(ID id) {
        return CustomException.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .message(String.format(MESSAGE_NOT_FOUND, entityClass.getSimpleName(), id))
                .build();
    }

    private T saveEntity(T entity) {
        try {
            return getRepository().saveAndFlush(entity);
        } catch (RuntimeException e) {
            throw CustomException.badRequest("Erro ao salvar entidade: " + e.getMessage());
        }
    }
}
