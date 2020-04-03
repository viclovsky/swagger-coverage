package com.github.viclovsky.swagger.coverage.core.results.builder.prebuilder;


import com.github.viclovsky.swagger.coverage.core.generator.OperationConditionGenerator;
import com.github.viclovsky.swagger.coverage.core.generator.SwaggerSpecificationProcessor;
import com.github.viclovsky.swagger.coverage.core.model.Condition;
import com.github.viclovsky.swagger.coverage.core.model.ConditionOperationCoverage;
import com.github.viclovsky.swagger.coverage.core.model.OperationKey;
import com.github.viclovsky.swagger.coverage.core.model.OperationsHolder;
import com.github.viclovsky.swagger.coverage.core.results.Results;
import com.github.viclovsky.swagger.coverage.core.results.builder.core.StatisticsPreBuilder;
import com.github.viclovsky.swagger.coverage.core.results.data.BranchStatistics;
import com.github.viclovsky.swagger.coverage.core.results.data.OperationResult;
import com.github.viclovsky.swagger.coverage.core.rule.core.ConditionRule;
import io.swagger.models.Operation;
import io.swagger.models.Swagger;
import io.swagger.models.parameters.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

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

            Map<String, Parameter> currentParams = entry.getValue().getParameters().stream().collect(Collectors.toMap(
                Parameter::getName,
                p->p
            ));

            log.info(String.format("current param map is %s",currentParams));

            if (mainCoverageData.containsKey(entry.getKey())) {
                mainCoverageData
                    .get(entry.getKey())
                    .increaseProcessCount()
                    .getConditions()
                    .stream()
                    .filter(Condition::isNeedCheck)
                    .forEach(branch -> branch.check(entry.getValue().getParameters(),entry.getValue().getResponses()))
                ;
            } else {
                log.info(String.format("oops. Missed request [%s]", entry.getKey()));

                missed.put(
                    entry.getKey(),
                    entry.getValue()
                );
            }
        });

        return this;
    }

    @Override
    public void build(Results results){
        Map<OperationKey, OperationResult> operations = new TreeMap<>();
        Map<String, BranchStatistics> branchStatisticsMap = new HashMap<>();

        mainCoverageData.entrySet().stream().forEach(entry -> {
            entry.getValue().getConditions().stream().filter(Condition::isHasPostCheck).forEach(branch -> branch.postCheck());

            operations.put(
                entry.getKey(),
                new OperationResult(entry.getValue().getConditions())
                    .setProcessCount(entry.getValue().getProcessCount())
                    .setDescription(entry.getValue().getOperation().getDescription())
                    .setOperationKey(entry.getKey())
            );

            entry.getValue().getConditions().stream().forEach(
                    branch -> {
                        if (!branchStatisticsMap.containsKey(branch.getType())){
                            branchStatisticsMap.put(
                                    branch.getType(),
                                    new BranchStatistics()
                            );
                        }

                        branchStatisticsMap.get(branch.getType()).processBranch(entry.getKey(),branch);
                    }
            );
        });

        results
            .setOperations(operations)
            .setMissed(missed)
            .setBranchStatisticsMap(branchStatisticsMap)
            ;
    }
}
