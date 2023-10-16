package com.ezpzapis.commons.repository;

import com.ezpzapis.commons.model.GenericEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@NoRepositoryBean
public interface GenericRepository<T extends GenericEntity> {
    Mono<T> findById(String id);
    Mono<T> save(T entity);
    Mono<T> update(T entity);
    Mono<Void> deleteById(String id);
    Flux<T> findAll();
    Flux<T> findByQuery(String query, Pageable page);

    Flux<T> findByQueryWithFields(List<String> fields, String query, Pageable page);
}
