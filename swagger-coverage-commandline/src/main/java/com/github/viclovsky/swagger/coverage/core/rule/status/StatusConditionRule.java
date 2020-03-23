package com.github.viclovsky.swagger.coverage.core.rule.status;

import com.github.viclovsky.swagger.coverage.core.model.Condition;
import com.github.viclovsky.swagger.coverage.core.rule.ConditionRule;
import io.swagger.models.Operation;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Base rule for status
 */
public abstract class StatusConditionRule extends ConditionRule {

    public abstract List<Condition> processStatus(String statusCode);

    public List<Condition> createCondition(Operation operation){
        return operation.getResponses()
                .keySet()
                .stream()
                .map(this::processStatus)
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .collect(toList());
    }
}
