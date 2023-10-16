package com.ezpzapis.commons.service;

import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface GenericService<T> {
    Mono<T> findById(String id);
    Mono<T> save(T entity);
    Mono<T> update(T entity);
    Mono<Void> deleteById(String id);
    Flux<T> findAll(Pageable page);
    Flux<T> findByQuery(String query, Pageable page);
    Flux<T> findByQueryWithFields(List<String> fields, String query, Pageable page);
    Mono<T> patch(String id, JsonPatch patch);
}
