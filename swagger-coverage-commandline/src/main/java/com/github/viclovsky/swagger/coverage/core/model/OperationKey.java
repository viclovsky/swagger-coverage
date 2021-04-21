package com.github.viclovsky.swagger.coverage.core.model;

import io.swagger.v3.oas.models.PathItem;

public class OperationKey implements Comparable {

    private String path;
    private PathItem.HttpMethod httpMethod;

    @Override
    public String toString() {
        return path + " " + httpMethod;
    }

    public String getPath() {
        return path;
    }

    public OperationKey setPath(String path) {
        this.path = path;
        return this;
    }

    public PathItem.HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public OperationKey setHttpMethod(PathItem.HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    @Override
    public int compareTo(Object o) {
        return this.toString().compareTo(o.toString());
    }
}
