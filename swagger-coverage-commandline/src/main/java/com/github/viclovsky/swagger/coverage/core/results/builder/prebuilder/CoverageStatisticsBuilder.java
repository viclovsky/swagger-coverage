package com.github.viclovsky.swagger.coverage.core.results.builder.prebuilder;

import com.github.viclovsky.swagger.coverage.configuration.Configuration;
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
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.parameters.PathParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CoverageStatisticsBuilder extends StatisticsPreBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(CoverageStatisticsBuilder.class);

    private Map<OperationKey, ConditionOperationCoverage> mainCoverageData;
    private Map<OperationKey, Operation> missed = new TreeMap<>();
    private Map<OperationKey, Operation> deprecated = new TreeMap<>();

    @Override
    public CoverageStatisticsBuilder configure(OpenAPI swagger, List<ConditionRule> rules) {
        mainCoverageData = OperationConditionGenerator.getOperationMap(swagger, rules);
        return this;
    }

    @Override
    public CoverageStatisticsBuilder add(OpenAPI swagger) {
        OperationsHolder operations = SwaggerSpecificationProcessor.extractOperation(swagger);

        operations.getOperations().forEach((key, value) -> {
            LOGGER.info(String.format("==  process result [%s]", key));

            Optional<OperationKey> keyOptional = mainCoverageData.keySet().stream()
                    .filter(equalsOperationKeys(key)).findFirst();

            if (keyOptional.isPresent()) {
                // Recreate path parameters if framework can't parse path parameters to value
                Map<String, String> extractedPathParams = new AntPathMatcher()
                        .extractUriTemplateVariables(keyOptional.get().getPath(), key.getPath());
                if (!extractedPathParams.isEmpty()) {
                    List<String> existedPathParametersNames = value.getParameters().stream()
                            .filter(parameter -> parameter.getIn().equals("path"))
                            .map(parameter -> parameter.getName())
                            .collect(Collectors.toList());
                    extractedPathParams.forEach((n, v) -> {
                        if (!existedPathParametersNames.contains(n)) {
                            value.addParametersItem(new PathParameter().name(n).example(v));
                            LOGGER.info(String.format("==  result [%s] was also mimicked by absent path param with [name=%s, example=%s]", key, n, v));
                        }
                    });
                }

                mainCoverageData.get(keyOptional.get())
                        .increaseProcessCount()
                        .getConditions()
                        .stream()
                        .filter(Condition::isNeedCheck)
                        .forEach(condition -> condition.check(value));
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
    public void build(Results results, Configuration configuration) {
        Map<OperationKey, OperationResult> operations = new TreeMap<>();
        Map<String, ConditionStatistics> conditionStatisticsMap = new HashMap<>();

        mainCoverageData.forEach((key, value) -> {
            value.getConditions().stream().filter(Condition::isHasPostCheck).forEach(Condition::postCheck);

            operations.put(key, new OperationResult(configuration, value.getConditions(), value.getOperation().getDeprecated())
                    .setProcessCount(value.getProcessCount())
                    .setDescription(value.getOperation().getDescription())
                    .setOperationKey(key)
            );

            if (value.getOperation().getDeprecated() != null && value.getOperation().getDeprecated()) {
                deprecated.put(key, value.getOperation());
            }

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
                .setDeprecated(deprecated)
                .setConditionStatisticsMap(conditionStatisticsMap);
    }
}
