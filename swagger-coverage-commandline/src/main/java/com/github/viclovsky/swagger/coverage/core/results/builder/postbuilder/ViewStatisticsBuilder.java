package com.github.viclovsky.swagger.coverage.core.results.builder.postbuilder;

import com.github.viclovsky.swagger.coverage.core.model.OperationKey;
import com.github.viclovsky.swagger.coverage.core.results.Results;
import com.github.viclovsky.swagger.coverage.core.results.builder.core.StatisticsOperationPostBuilder;
import com.github.viclovsky.swagger.coverage.core.results.data.OperationResult;
import com.github.viclovsky.swagger.coverage.core.results.data.ViewData;
import com.github.viclovsky.swagger.coverage.core.rule.core.ConditionRule;
import io.swagger.models.Swagger;

import java.util.List;

public class ViewStatisticsBuilder extends StatisticsOperationPostBuilder  {

    protected ViewData viewData;

    @Override
    public ViewStatisticsBuilder configure(Swagger swagger, List<ConditionRule> rules) {
        viewData = new ViewData(
            swagger.getInfo().getTitle() + swagger.getInfo().getVersion()
        );

        return this;
    }

    @Override
    public void buildOperation(OperationKey operation, OperationResult operationResult) {

    }

    @Override
    public void buildResult(Results results) {
        results.setViewData(viewData);
    }
}
