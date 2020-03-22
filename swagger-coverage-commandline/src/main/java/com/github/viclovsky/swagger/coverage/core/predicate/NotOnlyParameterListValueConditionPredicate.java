package com.github.viclovsky.swagger.coverage.core.predicate;

import com.github.viclovsky.swagger.coverage.core.generator.SwaggerSpecificationProcessor;
import io.swagger.models.Response;
import io.swagger.models.parameters.Parameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class NotOnlyParameterListValueConditionPredicate extends ConditionPredicate {
    private String name;
    private String in;

    private String reason;
    private List<String> expectedValue = new ArrayList<>();
    private List<String> currentValue = new ArrayList<>();

    public NotOnlyParameterListValueConditionPredicate(String name, String in, List<String> value) {
        this.name = name;
        this.in = in;
        this.expectedValue.addAll(value);
        reason = "Checked values: -";
    }

    @Override
    public boolean check(List<Parameter> params, Map<String, Response> responses) {
         Optional<Parameter> p = params.stream().filter(ParameterUtils.equalsParam(name, in)).findFirst();
         if (p.isPresent()) {
            String val = SwaggerSpecificationProcessor.extractValue(p.get());
            currentValue.add(val);
        }

        return true;
    }

    @Override
    public boolean postCheck() {
        reason = "Checked values: " + String.join(",", currentValue);
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

    public List<String> getValue() {
        return expectedValue;
    }

    public NotOnlyParameterListValueConditionPredicate setValue(List<String> value) {
        this.expectedValue = value;
        return this;
    }
}
