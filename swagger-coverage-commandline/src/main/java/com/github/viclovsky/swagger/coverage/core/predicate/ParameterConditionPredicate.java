package com.github.viclovsky.swagger.coverage.core.predicate;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.responses.ApiResponse;

import java.util.List;
import java.util.Map;

public abstract class ParameterConditionPredicate extends ConditionPredicate {

    @Override
    public boolean check(Operation operation) {
        return check(operation.getParameters(), operation.getResponses());
    }

    public abstract boolean check(List<Parameter> params, Map<String, ApiResponse> responses);

}
