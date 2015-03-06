package com.thathurleyguy.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ItemRequest {
    @JsonProperty
    private String itemId;
    @JsonProperty
    private String data;

    public String getData() {
        return data;
    }

    public String getItemId() {

        return itemId;
    }
}
