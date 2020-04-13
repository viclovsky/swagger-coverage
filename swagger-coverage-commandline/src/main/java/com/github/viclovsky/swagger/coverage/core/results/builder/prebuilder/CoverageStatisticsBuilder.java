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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CoverageStatisticsBuilder extends StatisticsPreBuilder {
    private static final Logger log = LoggerFactory.getLogger(CoverageStatisticsBuilder.class);

    protected Map<OperationKey, ConditionOperationCoverage> mainCoverageData;
    protected Map<OperationKey, Operation> missed  = new TreeMap<>();

    @Override
    public CoverageStatisticsBuilder configure(Swagger swagger, List<ConditionRule> rules) {
        mainCoverageData = OperationConditionGenerator.getOperationMap(
            swagger,rules,options.getGeneral().isPathCaseIgnore()
        );
        return this;
    }

    @Override
    public CoverageStatisticsBuilder add(Swagger swagger){
        OperationsHolder operations = SwaggerSpecificationProcessor.extractOperation(
            swagger,options.getGeneral().isPathCaseIgnore()
        );

        operations.getOperations().entrySet().stream().forEach(entry -> {
            log.info(String.format("==  process result [%s]", entry.getKey()));

            if (mainCoverageData.containsKey(entry.getKey())) {
                mainCoverageData.get(entry.getKey())
                        .increaseProcessCount()
                        .getConditions()
                        .stream()
                        .filter(Condition::isNeedCheck)
                        .forEach(condition -> condition.check(entry.getValue().getParameters(), entry.getValue().getResponses()));
            } else {
                log.info(String.format("Missed request [%s]", entry.getKey()));

                missed.put(
                        entry.getKey(),
                        entry.getValue()
                );
            }
        });

        return this;
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
                            conditionStatisticsMap.put(
                                    condition.getType(),
                                    new ConditionStatistics()
                            );
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
