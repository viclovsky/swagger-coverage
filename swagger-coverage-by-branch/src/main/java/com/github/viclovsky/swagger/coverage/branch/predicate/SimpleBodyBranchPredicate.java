package com.github.viclovsky.swagger.coverage.branch.predicate;

import io.swagger.models.Response;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.Parameter;

import java.util.Map;

public class SimpleBodyBranchPredicate extends BranchPredicate {

    @Override
    public boolean check(Map<String, Parameter> params, Map<String, Response> responses) {
        long bodyParamsCount = params.entrySet()
            .stream()
            .filter(entry -> entry.getValue() instanceof BodyParameter).count()
            ;

        return bodyParamsCount > 0;
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
}
