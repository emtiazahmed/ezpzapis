package com.ezpzapis.commons.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.*;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
public abstract class GenericAuditedEntity<T> extends GenericEntity<T>{
	
	@CreatedDate
	@JsonPropertyDescription("Date/Time at which the object was created")
    protected LocalDateTime createdDate;
	
	@LastModifiedDate
	@JsonPropertyDescription("Date/Time at which the object was last modified")
	protected LocalDateTime modifiedDate;
	
	@CreatedBy
	@JsonPropertyDescription("User who created this object")
	protected String createdBy;
	
	@LastModifiedBy
	@JsonPropertyDescription("User who last modified this object")
	protected String lastModifiedBy;

	@Version
	@JsonPropertyDescription("Object version")
	protected Long version;

}
