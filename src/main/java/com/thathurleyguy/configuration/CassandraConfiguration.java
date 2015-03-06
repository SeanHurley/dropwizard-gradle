package com.thathurleyguy.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class CassandraConfiguration {
    @NotEmpty
    @JsonProperty
    private String keyspace;

    @NotEmpty
    @JsonProperty
    private String contactPoint;

    public String getContactPoint() {
        return contactPoint;
    }

    public String getKeyspace() {
        return keyspace;
    }
}
