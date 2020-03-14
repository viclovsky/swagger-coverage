package com.github.viclovsky.swagger.coverage.branch.results.builder.postbuilder;

import com.github.viclovsky.swagger.coverage.branch.results.Results;
import com.github.viclovsky.swagger.coverage.branch.results.builder.core.StatisticsOperationPostBuilder;
import com.github.viclovsky.swagger.coverage.branch.results.data.OperationResult;
import com.github.viclovsky.swagger.coverage.branch.rule.core.BranchRule;
import io.swagger.models.Swagger;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ZeroCallStatisticsBuilder extends StatisticsOperationPostBuilder {

    protected Set<String> zeroCall  = new HashSet<>();

    public ZeroCallStatisticsBuilder(Swagger swagger, List<BranchRule> rules) {
        super(swagger, rules);
    }

    @Override
    public void buildOperation(String operation, OperationResult operationResult) {
        if (operationResult.getProcessCount() == 0){
            zeroCall.add(operation);
        }
    }

    @Override
    public void buildResult(Results results) {
        results.setZeroCall(zeroCall);
    }
}
