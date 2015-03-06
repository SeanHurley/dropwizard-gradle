package com.thathurleyguy.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class RedisConfiguration {
    @NotEmpty
    @JsonProperty
    private String hostname;

    @NotEmpty
    @JsonProperty
    private String port;

    public String getPort() {
        return port;
    }

    public String getHostname() {
        return hostname;
    }
}
