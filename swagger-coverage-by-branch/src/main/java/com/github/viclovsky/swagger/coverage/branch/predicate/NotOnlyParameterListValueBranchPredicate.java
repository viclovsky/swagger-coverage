package com.github.viclovsky.swagger.coverage.branch.predicate;

import com.github.viclovsky.swagger.coverage.branch.generator.OperationBranchGenerator;
import com.github.viclovsky.swagger.coverage.branch.generator.SwaggerSpecificationProccessor;
import io.swagger.models.Response;
import io.swagger.models.parameters.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NotOnlyParameterListValueBranchPredicate extends BranchPredicate {

    private static final Logger log = LoggerFactory.getLogger(OperationBranchGenerator.class);

    protected String name;
    protected String reason;
    protected List<String> expectedValue = new ArrayList<>();
    protected List<String> currentValue = new ArrayList<>();

    public NotOnlyParameterListValueBranchPredicate(String name, List<String> value) {
        this.name = name;
        this.expectedValue.addAll(value);

        reason = "Checked values: ...";
    }

    @Override
    public boolean check(Map<String, Parameter> params, Map<String, Response> responses) {
        if (params.containsKey(name)) {
            String val = SwaggerSpecificationProccessor.extractValue(params.get(name));
            log.info("read value " + val + " for " + name);
            if (!currentValue.contains(val)) {
                currentValue.add(val);
            }
        }

        return true;
    }

    @Override
    public boolean postCheck() {
        reason = "Checked values: " + String.join(",",currentValue);

        currentValue.removeAll(expectedValue);
        boolean covered = !currentValue.isEmpty();

        if (covered){
            reason = reason + "<br>" + "Not enum value: " + String.join(",",currentValue);
        }

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
