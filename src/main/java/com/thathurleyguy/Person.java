package com.thathurleyguy;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Person {

    private int id;
    private String name;

    public Person() {
        // Jackson deserialization
    }

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @JsonProperty
    public int getId() {
        return id;
    }

    @JsonProperty
    public String getName() {
        return name;
    }
}
