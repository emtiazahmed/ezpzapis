package com.ezpzapis.metastore.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class LookupItem implements Serializable {
    private String key;
    private String value;
    private String displayValue;
}
