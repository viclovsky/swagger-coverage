package com.github.viclovsky.swagger.coverage.core.rule.parameter;

import com.github.viclovsky.swagger.coverage.core.model.Condition;
import com.github.viclovsky.swagger.coverage.core.rule.ConditionRule;
import io.swagger.models.Operation;
import io.swagger.models.parameters.Parameter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Base rule for parameter
 */
public abstract class ParameterRule extends ConditionRule {

    public abstract List<Condition> processParameter(Parameter parameter);

    public List<Condition> createCondition(Operation operation) {
        return operation.getParameters()
                .stream()
                .map(this::processParameter)
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}
