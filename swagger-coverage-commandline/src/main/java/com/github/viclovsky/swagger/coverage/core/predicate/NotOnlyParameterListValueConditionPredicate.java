package com.github.viclovsky.swagger.coverage.core.predicate;

import com.github.viclovsky.swagger.coverage.core.generator.SwaggerSpecificationProcessor;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.responses.ApiResponse;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class NotOnlyParameterListValueConditionPredicate extends ParameterConditionPredicate {
    private String name;
    private String in;

    private String reason;
    private Set<String> expectedValue = new HashSet<>();
    private Set<String> currentValue = new HashSet<>();

    public NotOnlyParameterListValueConditionPredicate(String name, String in, List<String> value) {
        this.name = name;
        this.in = in;
        this.expectedValue.addAll(value);
        reason = "Checked values: -";
    }

    @Override
    public boolean check(List<Parameter> params, Map<String, ApiResponse> responses) {
        if (params != null) {
            Optional<Parameter> p = params.stream().filter(ParameterUtils.equalsParam(name, in)).findFirst();
            if (p.isPresent()) {
                String val = SwaggerSpecificationProcessor.extractValue(p.get());
                currentValue.add(val);
            }
        }

        return true;
    }

    @Override
    public boolean postCheck() {
        reason = "Checked values: " + currentValue.toString();
        currentValue.removeAll(expectedValue);
        return !currentValue.isEmpty();
    }

    @Override
    public boolean hasPostCheck() {
        return true;
    }

    @Override
    public String getReason() {
        return reason;
    }

    public String getName() {
        return name;
    }

    public NotOnlyParameterListValueConditionPredicate setName(String name) {
        this.name = name;
        return this;
    }

    public Set<String> getValue() {
        return expectedValue;
    }

    public NotOnlyParameterListValueConditionPredicate setValue(Set<String> value) {
        this.expectedValue = value;
        return this;
    }
}
