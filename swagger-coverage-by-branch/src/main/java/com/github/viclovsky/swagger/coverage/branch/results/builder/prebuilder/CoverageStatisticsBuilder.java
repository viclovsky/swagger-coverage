package com.github.viclovsky.swagger.coverage.branch.results.builder.prebuilder;

import com.github.viclovsky.swagger.coverage.branch.generator.OperationBranchGenerator;
import com.github.viclovsky.swagger.coverage.branch.generator.SwaggerSpecificationProccessor;
import com.github.viclovsky.swagger.coverage.branch.model.Branch;
import com.github.viclovsky.swagger.coverage.branch.model.BranchOperationCoverage;
import com.github.viclovsky.swagger.coverage.branch.model.OperationsHolder;
import com.github.viclovsky.swagger.coverage.branch.results.builder.core.StatisticsPreBuilder;
import com.github.viclovsky.swagger.coverage.branch.results.data.BranchStatistics;
import com.github.viclovsky.swagger.coverage.branch.results.data.OperationResult;
import com.github.viclovsky.swagger.coverage.branch.results.Results;
import com.github.viclovsky.swagger.coverage.branch.rule.core.BranchRule;
import io.swagger.models.Operation;
import io.swagger.models.Swagger;
import io.swagger.models.parameters.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class CoverageStatisticsBuilder extends StatisticsPreBuilder {
    private static final Logger log = LoggerFactory.getLogger(CoverageStatisticsBuilder.class);

    protected Map<String, BranchOperationCoverage> mainCoverageData;
    protected Map<String, Operation> missed  = new TreeMap<>();

    @Override
    public CoverageStatisticsBuilder configure(Swagger swagger, List<BranchRule> rules) {
        mainCoverageData = OperationBranchGenerator.getOperationMap(
            swagger,rules,options.getGeneral().isPathCaseIgnore()
        );
        return this;
    }

    @Override
    public CoverageStatisticsBuilder add(Swagger swagger){
        OperationsHolder operations = SwaggerSpecificationProccessor.extractOperation(
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
                    .getBranches()
                    .stream()
                    .filter(Branch::isNeedCheck)
                    .forEach(branch -> branch.check(currentParams,entry.getValue().getResponses()))
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
        Map<String, OperationResult> operations = new TreeMap<>();
        Map<String, BranchStatistics> branchStatisticsMap = new HashMap<>();

        mainCoverageData.entrySet().stream().forEach(entry -> {
            entry.getValue().getBranches().stream().filter(Branch::isHasPostCheck).forEach(branch -> branch.postCheck());

            operations.put(
                entry.getKey(),
                new OperationResult(entry.getValue().getBranches())
                    .setProcessCount(entry.getValue().getProcessCount())
                    .setDescription(entry.getValue().getOperation().getDescription())
            );

            entry.getValue().getBranches().stream().forEach(
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
