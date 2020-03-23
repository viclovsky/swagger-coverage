package com.github.viclovsky.swagger.coverage.branch.results.builder.core;

import com.github.viclovsky.swagger.coverage.branch.results.Results;
import com.github.viclovsky.swagger.coverage.branch.results.data.OperationResult;
import com.github.viclovsky.swagger.coverage.branch.rule.core.BranchRule;
import io.swagger.models.Swagger;

import java.util.List;

public abstract class StatisticsOperationPostBuilder extends StatisticsPostBuilder {

    @Override
    public StatisticsBuilder configure(Swagger swagger, List<BranchRule> rules) {
        return this;
    }

    @Override
    public void build(Results results) {
        results.getOperations().entrySet().forEach(
            entry -> buildOperation(entry.getKey(),entry.getValue())
        );

        buildResult(results);
    }

    public abstract void buildOperation(String operation, OperationResult operationResult);
    public abstract void buildResult(Results results);
}
