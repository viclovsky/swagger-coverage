package com.github.viclovsky.swagger.coverage.core.results.builder.postbuilder;

import com.github.viclovsky.swagger.coverage.core.model.OperationKey;
import com.github.viclovsky.swagger.coverage.core.results.Results;
import com.github.viclovsky.swagger.coverage.core.results.builder.core.StatisticsOperationPostBuilder;
import com.github.viclovsky.swagger.coverage.core.results.data.OperationResult;

import java.util.Map;
import java.util.TreeMap;

public class FlatOperationBuilder extends StatisticsOperationPostBuilder {
    @Override
    public void buildOperation(OperationKey operation, OperationResult operationResult) {

    }

    @Override
    public void buildResult(Results results) {
         Map<String, OperationResult> flatOperations = new TreeMap<>();

         results.getOperations().forEach((operationKey, operationResult) -> flatOperations.put(
             operationKey.toString(),operationResult)
         );

         results.setFlatOperations(flatOperations);
    }
}
