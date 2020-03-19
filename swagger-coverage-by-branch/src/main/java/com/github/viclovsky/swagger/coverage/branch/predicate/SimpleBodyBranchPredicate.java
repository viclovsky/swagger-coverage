package com.github.viclovsky.swagger.coverage.branch.predicate;

import io.swagger.models.Response;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.Parameter;

import java.util.List;
import java.util.Map;

public class SimpleBodyBranchPredicate extends BranchPredicate {

    @Override
    public boolean check(List<Parameter> params, Map<String, Response> responses) {
        long bodyParamsCount = params.stream().filter(entry -> entry instanceof BodyParameter).count();
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
