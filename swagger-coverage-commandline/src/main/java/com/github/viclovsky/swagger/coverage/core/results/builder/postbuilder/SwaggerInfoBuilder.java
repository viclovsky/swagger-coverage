package com.github.viclovsky.swagger.coverage.core.results.builder.postbuilder;

import com.github.viclovsky.swagger.coverage.core.model.OperationKey;
import com.github.viclovsky.swagger.coverage.core.results.Results;
import com.github.viclovsky.swagger.coverage.core.results.builder.core.StatisticsOperationPostBuilder;
import com.github.viclovsky.swagger.coverage.core.results.data.OperationResult;
import com.github.viclovsky.swagger.coverage.core.rule.core.ConditionRule;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import java.util.List;

public class SwaggerInfoBuilder extends StatisticsOperationPostBuilder {

    private Info info;

    @Override
    public SwaggerInfoBuilder configure(OpenAPI swagger, List<ConditionRule> rules) {
        info = swagger.getInfo();
        return this;
    }

    @Override
    public void buildOperation(OperationKey operation, OperationResult operationResult) {

    }

    @Override
    public void buildResult(Results results) {
        results.setInfo(info);
    }
}
