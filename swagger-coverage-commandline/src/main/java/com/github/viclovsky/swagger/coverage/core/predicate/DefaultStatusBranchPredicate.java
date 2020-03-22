package com.github.viclovsky.swagger.coverage.core.predicate;

import io.swagger.models.Response;
import io.swagger.models.parameters.Parameter;

import java.util.List;
import java.util.Map;

public class DefaultStatusBranchPredicate extends BranchPredicate {

    private String statusCode;

    public DefaultStatusBranchPredicate(String statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public boolean check(List<Parameter> params, Map<String, Response> responses) {
        return responses.containsKey(getStatusCode());
    }

    @Override
    public boolean postCheck() {
        return false;
    }

    @Override
    public boolean hasPostCheck() {
        return false;
    }

    @Override
    public String getReason() {
        return null;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return "StatusBranchPredicate{" +
                "statusCode=" + statusCode +
                '}';
    }
}
