package com.github.viclovsky.swagger.coverage.core.predicate;

import com.github.viclovsky.swagger.coverage.core.generator.SwaggerSpecificationProcessor;
import io.swagger.models.Response;
import io.swagger.models.parameters.Parameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DefaultParameterValueBranchPredicate extends BranchPredicate {

    private String name;
    private String in;

    private String reason;
    private String expectedValue;
    private List<String> currentValue = new ArrayList<>();

    public DefaultParameterValueBranchPredicate(String name, String in, String value) {
        this.name = name;
        this.in = in;
        this.expectedValue = value;
    }

    @Override
    public boolean check(List<Parameter> params, Map<String, Response> responses) {
        Optional<Parameter> p = params.stream().filter(ParameterUtils.equalsParam(name, in)).findFirst();

        if (p.isPresent()) {
            String val = SwaggerSpecificationProcessor.extractValue(p.get());
            currentValue.add(val);
        }

        return currentValue.contains(expectedValue);
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
        return reason;
    }

    public String getName() {
        return name;
    }

    public DefaultParameterValueBranchPredicate setName(String name) {
        this.name = name;
        return this;
    }

    public String getValue() {
        return expectedValue;
    }

    public DefaultParameterValueBranchPredicate setValue(String value) {
        this.expectedValue = value;
        return this;
    }
}
