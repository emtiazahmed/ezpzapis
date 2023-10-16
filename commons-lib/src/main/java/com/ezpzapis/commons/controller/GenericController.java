package com.ezpzapis.commons.controller;

import com.ezpzapis.commons.model.GenericEntity;
import com.ezpzapis.commons.service.GenericService;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.victools.jsonschema.generator.SchemaGenerator;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.ParameterizedType;
import java.util.List;

@SuppressWarnings("ALL")
@Log4j2
public class GenericController<T extends GenericEntity<T>, S extends GenericService<T>>{

	protected S service;

	@Autowired
	protected SchemaGenerator schemaGenerator;
	public GenericController(S service, SchemaGenerator schemaGenerator) {
		this.service = service;
		this.schemaGenerator = schemaGenerator;
	}

	Class<T> getParameterClass() {
		return (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@GetMapping
	@QueryMapping
	public Flux<T> findByQueryWithFields(@Parameter(description = "Comma-separated list of fields to fetch")
										 @RequestParam(value = "fields", required = false) String fields,
										 @Parameter(description = "Predicate to filter returned objects")
										 @RequestParam(value = "filter", required = false) String filter,
										 @Parameter(description = "Pagination and Sorting parameters", required = false)
										 Pageable page) {

		List<String> fieldsList = StringUtils.isNotBlank(fields) ? List.of(fields.split(",")) : List.of();
		return service.findByQueryWithFields(fieldsList, filter, page);
	}

	@GetMapping("/{id}")
	@QueryMapping
	public Mono<T> getById(@PathVariable String id) {
		log.info("getById " + id);
		Mono<T> fallback = Mono.error(new RuntimeException("Object not found"));
		return service.findById(id).switchIfEmpty(fallback);
	}
	
	@PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
	@MutationMapping
    public Mono<T> create(@Valid @RequestBody T entity){
        return service.save(entity);
    }

	@PutMapping("/{id}")
	@ResponseStatus(value = HttpStatus.CREATED)
	@MutationMapping
	public Mono<T> update(@PathVariable String id, @RequestBody T entity) {
		if(Strings.isNotBlank(entity.getId()) && id.equals(entity.getId()))
			return service.update(entity);
		else
			throw new RuntimeException("Id in the url and body do not match");
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	@MutationMapping
	public Mono<Void> delete(@PathVariable String id) {
		return service.deleteById(id);
	}

	@PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
	@MutationMapping
	public Mono<T> patch(@PathVariable String id, @RequestBody JsonPatch patch) {
		return service.patch(id, patch);
	}

	@GetMapping(value = "/schema", produces = "application/schema+json")
	public Mono<JsonNode> getSchema() {
		return Mono.just(schemaGenerator.generateSchema(getParameterClass()));
	}
}
