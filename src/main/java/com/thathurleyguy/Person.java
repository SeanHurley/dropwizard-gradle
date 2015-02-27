package com.thathurleyguy;

import com.datastax.driver.core.Row;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Person {

    private UUID id;
    private String name;

    public Person() {
    }

    public Person(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public Person(Row row) {
        this.id = row.getUUID("id");
        this.name = row.getString("name");
    }

    @JsonProperty
    public UUID getId() {
        return id;
    }

    @JsonProperty
    public String getName() {
        return name;
    }
}
