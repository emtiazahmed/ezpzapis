package com.ezpzapis.commons.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * https://ultimate.systems/web/blog/generic-controllers-and-services-in-spring-boot-java
 */

@Data
@SuperBuilder
@NoArgsConstructor
@Log4j2
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class GenericEntity<T> implements Serializable {

	@Id
	@JsonPropertyDescription("Autogenerated unique Identifier")
	protected String id;

}
