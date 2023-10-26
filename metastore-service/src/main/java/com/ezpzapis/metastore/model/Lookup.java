package com.ezpzapis.metastore.model;

import com.ezpzapis.commons.model.GenericAuditedEntity;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "lookup")
public class Lookup extends GenericAuditedEntity<Lookup> {
    private String name;
    private String displayName;

}
