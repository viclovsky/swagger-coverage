package com.github.viclovsky.swagger.coverage.core.generator;

import com.github.viclovsky.swagger.coverage.core.model.Condition;
import com.github.viclovsky.swagger.coverage.core.model.ConditionOperationCoverage;
import com.github.viclovsky.swagger.coverage.core.model.OperationKey;
import com.github.viclovsky.swagger.coverage.core.model.OperationsHolder;
import com.github.viclovsky.swagger.coverage.core.rule.core.ConditionRule;
import io.swagger.models.Operation;
import io.swagger.models.Swagger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class OperationConditionGenerator {

    private static final Logger log = LoggerFactory.getLogger(OperationConditionGenerator.class);

    public static Map<OperationKey, ConditionOperationCoverage> getOperationMap(Swagger swagger, List<ConditionRule> rules){
        return getOperationMap(swagger,rules,false);
    }

    public static Map<OperationKey, ConditionOperationCoverage> getOperationMap(Swagger swagger, List<ConditionRule> rules, boolean ignoreCase) {
        OperationsHolder operations = SwaggerSpecificationProcessor.extractOperation(swagger);
        Map<OperationKey, ConditionOperationCoverage> coverage = new TreeMap<>();

        operations.getOperations().forEach((key, value) -> {
            ConditionOperationCoverage oc = buildConditionOperationCoverage(value, rules);
            log.debug(String.format("put operation %s", key));
            coverage.put(key, oc);
        });

        return coverage;
    }

    static ConditionOperationCoverage buildConditionOperationCoverage(Operation operation, List<ConditionRule> rules){
        ConditionOperationCoverage operationCoverage = new ConditionOperationCoverage();
        operationCoverage.setOperation(operation);
        operationCoverage.setConditions(generateConditionList(operation,rules));

        return operationCoverage;
    }

    static List<Condition> generateConditionList(Operation operation, List<ConditionRule> rules){
        List<Condition> conditions = rules
                .stream()
                .map(rule -> rule.createCondition(operation))
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        log.debug(String.format("created list is %s", conditions));
        return conditions;
    }
}
