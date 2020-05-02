package com.github.viclovsky.swagger.coverage.core.results.builder.prebuilder;


import com.github.viclovsky.swagger.coverage.core.generator.OperationConditionGenerator;
import com.github.viclovsky.swagger.coverage.core.generator.SwaggerSpecificationProcessor;
import com.github.viclovsky.swagger.coverage.core.model.Condition;
import com.github.viclovsky.swagger.coverage.core.model.ConditionOperationCoverage;
import com.github.viclovsky.swagger.coverage.core.model.OperationKey;
import com.github.viclovsky.swagger.coverage.core.model.OperationsHolder;
import com.github.viclovsky.swagger.coverage.core.results.Results;
import com.github.viclovsky.swagger.coverage.core.results.builder.core.StatisticsPreBuilder;
import com.github.viclovsky.swagger.coverage.core.results.data.ConditionStatistics;
import com.github.viclovsky.swagger.coverage.core.results.data.OperationResult;
import com.github.viclovsky.swagger.coverage.core.rule.core.ConditionRule;
import io.swagger.models.Operation;
import io.swagger.models.Swagger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Predicate;

public class CoverageStatisticsBuilder extends StatisticsPreBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(CoverageStatisticsBuilder.class);

    private Map<OperationKey, ConditionOperationCoverage> mainCoverageData;
    private Map<OperationKey, Operation> missed = new TreeMap<>();

    @Override
    public CoverageStatisticsBuilder configure(Swagger swagger, List<ConditionRule> rules) {
        mainCoverageData = OperationConditionGenerator.getOperationMap(swagger, rules);
        return this;
    }

    @Override
    public CoverageStatisticsBuilder add(Swagger swagger) {
        OperationsHolder operations = SwaggerSpecificationProcessor.extractOperation(swagger);

        operations.getOperations().forEach((key, value) -> {
            LOGGER.info(String.format("==  process result [%s]", key));

            Optional<OperationKey> keyOptional = mainCoverageData.keySet().stream()
                    .filter(equalsOperationKeys(key)).findFirst();

            if (keyOptional.isPresent()) {
                mainCoverageData.get(keyOptional.get())
                        .increaseProcessCount()
                        .getConditions()
                        .stream()
                        .filter(Condition::isNeedCheck)
                        .forEach(condition -> condition.check(value.getParameters(), value.getResponses()));
            } else {
                LOGGER.info(String.format("Missed request [%s]", key));
                missed.put(key, value);
            }
        });
        return this;
    }

    private static Predicate<OperationKey> equalsOperationKeys(OperationKey operationKey) {
        return p -> (p.getHttpMethod() == operationKey.getHttpMethod())
                && new AntPathMatcher().match(p.getPath(), operationKey.getPath());
    }

    @Override
    public void build(Results results) {
        Map<OperationKey, OperationResult> operations = new TreeMap<>();
        Map<String, ConditionStatistics> conditionStatisticsMap = new HashMap<>();

        mainCoverageData.forEach((key, value) -> {
            value.getConditions().stream().filter(Condition::isHasPostCheck).forEach(Condition::postCheck);

            operations.put(key, new OperationResult(value.getConditions())
                    .setProcessCount(value.getProcessCount())
                    .setDescription(value.getOperation().getDescription())
                    .setOperationKey(key)
            );

            value.getConditions().forEach(condition -> {
                        if (!conditionStatisticsMap.containsKey(condition.getType())) {
                            conditionStatisticsMap.put(condition.getType(), new ConditionStatistics());
                        }
                        conditionStatisticsMap.get(condition.getType()).processCondition(key, condition);
                    }
            );
        });

        results.setOperations(operations)
                .setMissed(missed)
                .setConditionStatisticsMap(conditionStatisticsMap);
    }
}
