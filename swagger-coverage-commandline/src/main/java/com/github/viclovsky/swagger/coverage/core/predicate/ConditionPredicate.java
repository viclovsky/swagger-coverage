package com.github.viclovsky.swagger.coverage.core.predicate;

import io.swagger.models.Response;
import io.swagger.models.parameters.Parameter;

import java.util.List;
import java.util.Map;

public abstract class ConditionPredicate {

    public abstract boolean check(List<Parameter> params, Map<String, Response> responses);
    public abstract boolean postCheck();
    public abstract boolean hasPostCheck();
    public abstract String getReason();
}
