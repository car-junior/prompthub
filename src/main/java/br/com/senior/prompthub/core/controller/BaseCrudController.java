package br.com.senior.prompthub.core.controller;

import br.com.senior.prompthub.core.dto.PageParams;
import br.com.senior.prompthub.core.dto.PageResult;
import br.com.senior.prompthub.core.service.BaseService;
import br.com.senior.prompthub.core.service.modelmapper.ModelMapperService;
import br.com.senior.prompthub.core.specification.BaseSpecification;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

/**
 * Helper para operações CRUD com conversão automática de DTOs
 * <p>
 * Uso:
 * private final CrudHelper<Product, Long> crud;
 * <p>
 * public ProductController(ProductService service, AbstractModelMapperService mapper) {
 * this.crud = new CrudHelper<>(service, mapper, Product.class);
 * }
 */
public class BaseCrudController<ENTITY, ID> {

    /**
     * -- GETTER --
     * Acesso ao service para operações customizadas
     */
    @Getter
    private final BaseService<ENTITY, ID> service;
    /**
     * -- GETTER --
     * Acesso ao mapper para conversões customizadas
     */
    @Getter
    private final ModelMapperService<ENTITY> mapper;
    private final Class<ENTITY> entityClass;

    public BaseCrudController(BaseService<ENTITY, ID> service,
                              ModelMapperService<ENTITY> mapper,
                              Class<ENTITY> entityClass) {
        this.service = service;
        this.mapper = mapper;
        this.entityClass = entityClass;
    }

    /**
     * Lista todos com paginação
     */
    public QueryResult getAll(PageParams pageParams) {
        var pageRequest = PageRequest.of(
                pageParams.getPage(),
                pageParams.getItemsPerPage(),
                Sort.by(pageParams.getSort(), pageParams.getSortName())
        );

        var page = service.getAll(pageRequest);
        return new QueryResult(page);
    }

    /**
     * Lista todos com paginação e filtros (Specification)
     */
    public <FILTER> QueryResult getAllSpec(PageParams pageParams,
                                           BaseSpecification<ENTITY, FILTER> specification,
                                           FILTER filter) {
        var pageRequest = PageRequest.of(
                pageParams.getPage(),
                pageParams.getItemsPerPage(),
                Sort.by(pageParams.getSort(), pageParams.getSortName())
        );

        var predicate = specification.getPredicate(filter);
        var page = service.getAll(predicate, pageRequest);
        return new QueryResult(page);
    }

    /**
     * Busca por ID
     */
    public QueryResult getById(ID id) {
        var entity = service.getById(id);
        return new QueryResult(entity);
    }

    /**
     * Cria novo recurso
     */
    public <REQUEST_DTO> QueryResult create(REQUEST_DTO requestDto) {
        var entity = mapper.toObject(entityClass, requestDto);
        var created = service.create(entity);
        return new QueryResult(created);
    }

    /**
     * Atualiza recurso existente
     */
    public <REQUEST_DTO> QueryResult update(ID id, REQUEST_DTO requestDto) {
        var entity = mapper.toObject(entityClass, requestDto);
        var updated = service.update(id, entity);
        return new QueryResult(updated);
    }

    /**
     * Deleta recurso
     */
    public ResponseEntity<Void> delete(ID id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Classe interna para conversão fluente
     */
    public class QueryResult {
        private final Page<ENTITY> page;
        private final ENTITY entity;

        private QueryResult(Page<ENTITY> page) {
            this.page = page;
            this.entity = null;
        }

        private QueryResult(ENTITY entity) {
            this.page = null;
            this.entity = entity;
        }

        /**
         * Converte o resultado para o DTO especificado
         */
        public <DTO> ResponseEntity<PageResult<DTO>> asPageDto(Class<DTO> dtoClass) {
            return ResponseEntity.ok(mapper.toPage(dtoClass, page));
        }

        public <DTO> ResponseEntity<DTO> asDto(Class<DTO> dtoClass) {
            return ResponseEntity.ok(mapper.toObject(dtoClass, entity));
        }

        /**
         * Retorna sem conversão (entidade)
         */
        public ResponseEntity<PageResult<ENTITY>> asPageEntity() {
            return ResponseEntity.ok(mapper.toPage(entityClass, page));
        }

        public ResponseEntity<ENTITY> asEntity() {
            return ResponseEntity.ok(entity);
        }
    }

}
