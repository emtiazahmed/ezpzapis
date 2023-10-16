package com.ezpzapis.tenants.model;

import com.ezpzapis.commons.model.GenericAuditedEntity;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Document(collection = "customers")
public class Customer extends GenericAuditedEntity<Customer> {
    /**
     * Display name of tenant
     */
    @NotNull
    @JsonPropertyDescription("Name of Customer")
    private String name;

    /**
     * Url-friendly name of tenant
     */
    @NotNull
    @JsonPropertyDescription("Url-friendly name of Customer")
    private String slug;

    @JsonPropertyDescription("Key/Value pairs of Customer Configuration")
    private CustomerConfiguration config;

}
