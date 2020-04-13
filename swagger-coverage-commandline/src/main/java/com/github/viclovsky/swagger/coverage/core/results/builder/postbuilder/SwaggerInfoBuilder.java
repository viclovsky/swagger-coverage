package com.github.viclovsky.swagger.coverage.core.results.builder.postbuilder;

import com.github.viclovsky.swagger.coverage.core.model.OperationKey;
import com.github.viclovsky.swagger.coverage.core.results.Results;
import com.github.viclovsky.swagger.coverage.core.results.builder.core.StatisticsOperationPostBuilder;
import com.github.viclovsky.swagger.coverage.core.results.data.OperationResult;
import com.github.viclovsky.swagger.coverage.core.rule.core.ConditionRule;
import io.swagger.models.Info;
import io.swagger.models.Swagger;

import java.util.List;

public class SwaggerInfoBuilder extends StatisticsOperationPostBuilder {

    private Info info;

    @Override
    public SwaggerInfoBuilder configure(Swagger swagger, List<ConditionRule> rules) {
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
