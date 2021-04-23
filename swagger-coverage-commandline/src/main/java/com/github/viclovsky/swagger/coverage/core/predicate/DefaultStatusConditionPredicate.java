package com.github.viclovsky.swagger.coverage.core.predicate;

import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.responses.ApiResponse;

import java.util.List;
import java.util.Map;

public class DefaultStatusConditionPredicate extends ParameterConditionPredicate {

    private String statusCode;

    public DefaultStatusConditionPredicate(String statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public boolean check(List<Parameter> params, Map<String, ApiResponse> responses) {
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
        return "StatusConditionPredicate{" +
                "statusCode=" + statusCode +
                '}';
    }
}
