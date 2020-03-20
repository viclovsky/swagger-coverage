package com.github.viclovsky.swagger.coverage.branch.model;

import io.swagger.models.HttpMethod;

public class OperationKey implements Comparable {

    private String path;
    private HttpMethod httpMethod;

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

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public OperationKey setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    @Override
    public int compareTo(Object o) {
        return this.toString().compareTo(o.toString());
    }
}
