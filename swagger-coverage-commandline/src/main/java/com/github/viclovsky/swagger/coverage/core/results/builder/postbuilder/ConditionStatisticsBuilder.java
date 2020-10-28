package com.github.viclovsky.swagger.coverage.core.results.builder.postbuilder;

import com.github.viclovsky.swagger.coverage.core.model.OperationKey;
import com.github.viclovsky.swagger.coverage.core.results.Results;
import com.github.viclovsky.swagger.coverage.core.results.builder.core.StatisticsOperationPostBuilder;
import com.github.viclovsky.swagger.coverage.core.results.data.ConditionCounter;
import com.github.viclovsky.swagger.coverage.core.results.data.CoverageOperationMap;
import com.github.viclovsky.swagger.coverage.core.results.data.OperationResult;

public class ConditionStatisticsBuilder extends StatisticsOperationPostBuilder {

    private CoverageOperationMap coverageOperationMap = new CoverageOperationMap();
    private ConditionCounter conditionCounter = new ConditionCounter();

    @Override
    public void buildResult(Results results) {
        results.setCoverageOperationMap(coverageOperationMap)
                .setConditionCounter(conditionCounter);
    }

    @Override
    public void buildOperation(OperationKey operation, OperationResult operationResult) {
        conditionCounter.updateAll(operationResult.getAllConditionCount());
        conditionCounter.updateCovered(operationResult.getCoveredConditionCount());

        switch (operationResult.getState()) {
            case PARTY:
                coverageOperationMap.addParty(operation);
                break;
            case EMPTY:
                coverageOperationMap.addEmpty(operation);
                break;
            case FULL:
                coverageOperationMap.addFull(operation);
                break;
            case DEPRECATED:
                coverageOperationMap.addDeprecated(operation);
                break;
        }
    }
}
