package com.ezpzapis.commons.repository.impl;

import com.ezpzapis.commons.model.GenericEntity;
import com.ezpzapis.commons.repository.GenericRepository;
import com.ezpzapis.commons.rest.dsl.filter.RestFilterDSLLexer;
import com.ezpzapis.commons.rest.dsl.filter.RestFilterDSLMongoVisitor;
import com.ezpzapis.commons.rest.dsl.filter.RestFilterDSLParser;
import lombok.extern.log4j.Log4j2;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.NoRepositoryBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.ParameterizedType;
import java.util.List;

@Log4j2
@NoRepositoryBean
//@Observed
public class GenericRepositoryImpl<T extends GenericEntity> implements GenericRepository<T> {
    protected ReactiveMongoTemplate mongoTemplate;
    @Autowired
    public GenericRepositoryImpl(ReactiveMongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    Class<T> getParameterClass() {
        return (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public Mono<T> findById(String id) {
        return mongoTemplate.findById(id, getParameterClass());
    }

    @Override
    public Mono<T> save(T entity) {
        return mongoTemplate.save(entity);
    }

    @Override
    public Mono<T> update(T entity) {
        if (StringUtils.isBlank(entity.getId()))
            throw new RuntimeException("Id is missing.");
        Mono<T> fallback = Mono.error(new RuntimeException("Object Not Found"));
        return mongoTemplate.findById(entity.getId(), getParameterClass())
                .flatMap(foundEntity -> {
                            return mongoTemplate.save(entity);
                })
                .switchIfEmpty(fallback);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return mongoTemplate.remove(findById(id).block()).then();
    }

    @Override
    public Flux<T> findAll() {
        return mongoTemplate.findAll(getParameterClass());
    }

    @Override
    public Flux<T> findByQuery(String query, Pageable page) {
        return findByQueryWithFields(List.of(), query, page);
    }

    @Override
    public Flux<T> findByQueryWithFields(List<String> fields, String query, Pageable page) {
        Query mongoQuery = new Query();
        Criteria criteria = getCriteriaFromFilter(query);
        if( criteria != null) {
            log.info(String.format("Filter: %s", criteria.getCriteriaObject().toJson()));
            mongoQuery.addCriteria(criteria);
        }
        if ( fields != null && fields.size() > 0) {
            mongoQuery.fields().include(fields.stream().toArray(String[]::new));
        }
        return mongoTemplate.find(mongoQuery.with(page), getParameterClass());
    }

    /**
     * Converts a filter to Criteria object
     * @param filter
     * @return
     */
    private Criteria getCriteriaFromFilter(String filter) {

        if (StringUtils.isBlank(filter)) return null;

        CharStream input = CharStreams.fromString(filter);
        RestFilterDSLLexer lexer = new RestFilterDSLLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        RestFilterDSLParser parser = new RestFilterDSLParser(tokens);
        ParseTree tree = parser.query();
        System.out.println(String.format("Expression Tree: \n%s\n%s", tree.toStringTree(parser),tree.getText()));
        RestFilterDSLMongoVisitor visitor = new RestFilterDSLMongoVisitor(getParameterClass());

        return (Criteria) visitor.visit(tree);
    }


}
