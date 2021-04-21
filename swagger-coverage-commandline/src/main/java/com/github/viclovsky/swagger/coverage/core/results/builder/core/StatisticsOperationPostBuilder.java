package com.github.viclovsky.swagger.coverage.core.results.builder.core;

import com.github.viclovsky.swagger.coverage.configuration.Configuration;
import com.github.viclovsky.swagger.coverage.core.model.OperationKey;
import com.github.viclovsky.swagger.coverage.core.results.Results;
import com.github.viclovsky.swagger.coverage.core.results.data.OperationResult;
import com.github.viclovsky.swagger.coverage.core.rule.core.ConditionRule;
import io.swagger.v3.oas.models.OpenAPI;

import java.util.List;

public abstract class StatisticsOperationPostBuilder extends StatisticsPostBuilder {

    @Override
    public StatisticsBuilder configure(OpenAPI swagger, List<ConditionRule> rules) {
        return this;
    }

    @Override
    public void build(Results results, Configuration configuration) {
        results.getOperations().forEach(this::buildOperation);
        buildResult(results);
    }

    public abstract void buildOperation(OperationKey operation, OperationResult operationResult);

    public abstract void buildResult(Results results);
}
