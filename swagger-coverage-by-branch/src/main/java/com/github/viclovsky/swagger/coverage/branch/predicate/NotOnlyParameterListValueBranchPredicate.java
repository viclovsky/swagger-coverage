package com.github.viclovsky.swagger.coverage.branch.predicate;

import com.github.viclovsky.swagger.coverage.branch.generator.SwaggerSpecificationProccessor;
import io.swagger.models.Response;
import io.swagger.models.parameters.Parameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NotOnlyParameterListValueBranchPredicate extends BranchPredicate {

    protected String name;
    protected String reason;
    protected List<String> expectedValue = new ArrayList<>();
    protected List<String> currentValue = new ArrayList<>();

    public NotOnlyParameterListValueBranchPredicate(String name, List<String> value) {
        this.name = name;
        this.expectedValue.addAll(value);

        reason = "Missed values: " + String.join(",",expectedValue);
    }

    @Override
    public boolean check(Map<String, Parameter> params, Map<String, Response> responses) {
        if (params.containsKey(name)) {
            String val = SwaggerSpecificationProccessor.extractValue(params.get(name));
            currentValue.add(val);
        }

        return true;
    }

    @Override
    public boolean postCheck() {
        reason = "Current values: " + String.join(",",currentValue);

        currentValue.removeAll(expectedValue);
        boolean covered = !currentValue.isEmpty();

        return covered;
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

    public NotOnlyParameterListValueBranchPredicate setName(String name) {
        this.name = name;
        return this;
    }

    public List<String> getValue() {
        return expectedValue;
    }

    public NotOnlyParameterListValueBranchPredicate setValue(List<String> value) {
        this.expectedValue = value;
        return this;
    }
}
