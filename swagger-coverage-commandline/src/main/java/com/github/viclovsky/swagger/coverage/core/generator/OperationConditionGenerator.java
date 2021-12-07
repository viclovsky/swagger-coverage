package com.github.viclovsky.swagger.coverage.core.generator;

import com.github.viclovsky.swagger.coverage.core.model.Condition;
import com.github.viclovsky.swagger.coverage.core.model.ConditionOperationCoverage;
import com.github.viclovsky.swagger.coverage.core.model.OperationKey;
import com.github.viclovsky.swagger.coverage.core.model.OperationsHolder;
import com.github.viclovsky.swagger.coverage.core.rule.core.ConditionRule;
import com.github.viclovsky.swagger.coverage.core.rule.tag.FilterAllByTagRule;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class OperationConditionGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(OperationConditionGenerator.class);

    public static Map<OperationKey, ConditionOperationCoverage> getOperationMap(OpenAPI swagger, List<ConditionRule> rules) {
        OperationsHolder operations = filterByTags(SwaggerSpecificationProcessor.extractOperation(swagger), rules);

        Map<OperationKey, ConditionOperationCoverage> coverage = new TreeMap<>();

        operations.getOperations().forEach((key, value) -> {
            ConditionOperationCoverage oc = buildConditionOperationCoverage(value, rules);
            LOGGER.debug(String.format("put operation %s", key));
            coverage.put(key, oc);
        });

        return coverage;
    }

    private static ConditionOperationCoverage buildConditionOperationCoverage(Operation operation, List<ConditionRule> rules) {
        ConditionOperationCoverage operationCoverage = new ConditionOperationCoverage();
        operationCoverage.setOperation(operation);
        operationCoverage.setConditions(generateConditionList(operation, rules));

        return operationCoverage;
    }

    private static List<Condition> generateConditionList(Operation operation, List<ConditionRule> rules) {
        List<Condition> conditions = rules
                .stream()
                .map(rule -> rule.createCondition(operation))
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        LOGGER.debug(String.format("created list is %s", conditions));
        return conditions;
    }

    private static OperationsHolder filterByTags(OperationsHolder operations, List<ConditionRule> rules) {
        List<ConditionRule> tagRules = rules.stream()
                .filter(rule -> rule.getClass().equals(FilterAllByTagRule.class))
                .collect(Collectors.toList());
        if (tagRules.size() == 0) {
            return operations;
        }

        FilterAllByTagRule rule = (FilterAllByTagRule) tagRules.get(0);
        if (!rule.isRuleEnabled()) {
            return operations;
        }

        OperationsHolder result = new OperationsHolder();
        for (Map.Entry<OperationKey, Operation> entry : operations.getOperations().entrySet()) {
            Operation operation = entry.getValue();
            List<Condition> conditions = rule.createCondition(operation);
            if (conditions != null && !conditions.isEmpty()) {
                result.addOperation(entry.getKey(), operation);
            }
        }

        return result;
    }

}
