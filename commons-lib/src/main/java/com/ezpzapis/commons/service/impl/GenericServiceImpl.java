package com.ezpzapis.commons.service.impl;

import com.ezpzapis.commons.model.GenericEntity;
import com.ezpzapis.commons.repository.GenericRepository;
import com.ezpzapis.commons.service.GenericService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class GenericServiceImpl<T extends GenericEntity, R extends GenericRepository<T>> implements GenericService<T> {

    protected final R repository;
    protected final ObjectMapper objectMapper;

    @Autowired
    public GenericServiceImpl(R repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<T> findById(String id) {

        return this.repository.findById(id);
    }
    @Override
    public Mono<T> save(T entity) {
        return this.repository.save(entity);
    }

    @Override
    public Mono<T> update(T entity) {
        return this.repository.update(entity);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return this.repository.deleteById(id);
    }
    @Override
    public Flux<T> findAll(Pageable page) {
        return this.repository.findByQuery("", page);
    }
    @Override
    public Flux<T> findByQuery(String query, Pageable page) {
        return this.repository.findByQuery(query, page);
    }

    @Override
    public Flux<T> findByQueryWithFields(List<String> fields, String query, Pageable page) {
        return this.repository.findByQueryWithFields(fields, query, page);
    }

    @Override
    public Mono<T> patch(String id, JsonPatch patch) {
        return this.repository.findById(id).flatMap(existingEntity -> {
            try {
                T patched = applyPatchToEntity(patch, existingEntity);
                return this.repository.save(patched);
            } catch (JsonPatchException e) {
                throw new RuntimeException(e);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private T applyPatchToEntity(JsonPatch patch, T entity) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(entity, JsonNode.class));
        return (T) objectMapper.treeToValue(patched, entity.getClass());
    }
}
