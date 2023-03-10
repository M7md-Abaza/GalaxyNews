package com.example.galaxynews.pojo;

import java.util.LinkedHashMap;
import java.util.Map;

public class Source {
    private Object id;
    private String name;
    private final Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
