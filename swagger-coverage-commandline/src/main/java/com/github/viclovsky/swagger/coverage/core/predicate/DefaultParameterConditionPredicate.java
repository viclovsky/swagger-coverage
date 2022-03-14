package com.github.viclovsky.swagger.coverage.core.predicate;

import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.responses.ApiResponse;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DefaultParameterConditionPredicate extends ParameterConditionPredicate {

    private boolean isEmpty;
    private String name;
    private String in;

    public DefaultParameterConditionPredicate(boolean isEmpty, String name, String in) {
        this.isEmpty = isEmpty;
        this.name = name;
        this.in = in;
    }

    @Override
    public boolean check(List<Parameter> params, Map<String, ApiResponse> responses) {
        if (params != null) {
            Optional<Parameter> p = params.stream().filter(ParameterUtils.equalsParam(name, in)).findFirst();
            return (isEmpty() ^ p.isPresent());
        }
        return isEmpty();
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

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public String getName() {
        return name;
    }

    public void setParamName(String paramName) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ConditionPredicate{" +
                "isEmpty=" + isEmpty +
                ", paramName='" + name + '\'' +
                '}';
    }

}
