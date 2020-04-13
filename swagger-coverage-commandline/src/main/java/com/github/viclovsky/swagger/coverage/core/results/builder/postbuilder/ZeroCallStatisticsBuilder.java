package com.github.viclovsky.swagger.coverage.core.results.builder.postbuilder;

import com.github.viclovsky.swagger.coverage.core.model.OperationKey;
import com.github.viclovsky.swagger.coverage.core.results.Results;
import com.github.viclovsky.swagger.coverage.core.results.builder.core.StatisticsOperationPostBuilder;
import com.github.viclovsky.swagger.coverage.core.results.data.OperationResult;

import java.util.HashSet;
import java.util.Set;

public class ZeroCallStatisticsBuilder extends StatisticsOperationPostBuilder {

    private Set<OperationKey> zeroCall = new HashSet<>();

    @Override
    public void buildOperation(OperationKey operation, OperationResult operationResult) {
        if (operationResult.getProcessCount() == 0) {
            zeroCall.add(operation);
        }
    }

    @Override
    public void buildResult(Results results) {
        results.setZeroCall(zeroCall);
    }
}
