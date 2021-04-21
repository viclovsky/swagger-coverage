package com.github.viclovsky.swagger.coverage.core.rule.parameter;

import com.github.viclovsky.swagger.coverage.core.model.Condition;
import com.github.viclovsky.swagger.coverage.core.rule.core.ConditionRule;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.parameters.Parameter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class ParameterConditionRule extends ConditionRule {

    public abstract Condition processParameter(Parameter parameter);

    public List<Condition> createCondition(Operation operation) {
        if(operation.getParameters() != null) {
            return operation
                    .getParameters()
                    .stream()
                    .map(this::processParameter)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }

}
