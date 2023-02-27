package com.example.galaxynews.pojo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HomeResponse {

    private String status;
    private Integer totalResults;
    private List<Article> articles;
    private final Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
