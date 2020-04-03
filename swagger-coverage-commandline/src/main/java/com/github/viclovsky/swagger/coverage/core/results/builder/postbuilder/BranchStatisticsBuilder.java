package com.github.viclovsky.swagger.coverage.core.results.builder.postbuilder;

import com.github.viclovsky.swagger.coverage.core.model.OperationKey;
import com.github.viclovsky.swagger.coverage.core.results.Results;
import com.github.viclovsky.swagger.coverage.core.results.builder.core.StatisticsOperationPostBuilder;
import com.github.viclovsky.swagger.coverage.core.results.data.BranchCounter;
import com.github.viclovsky.swagger.coverage.core.results.data.CoverageOperationMap;
import com.github.viclovsky.swagger.coverage.core.results.data.OperationResult;

public class BranchStatisticsBuilder extends StatisticsOperationPostBuilder {

    protected CoverageOperationMap coverageOperationMap = new CoverageOperationMap();
    protected BranchCounter branchCounter = new BranchCounter();

    @Override
    public void buildResult(Results results) {
        results
            .setCoverageOperationMap(coverageOperationMap)
            .setBranchCounter(branchCounter)
            ;
    }

    @Override
    public void buildOperation(OperationKey operation, OperationResult operationResult) {
        branchCounter.updateAll(operationResult.getAllBrancheCount());
        branchCounter.updateCovered(operationResult.getCoveredBrancheCount());

        switch (operationResult.getState()){
            case PARTY:
                coverageOperationMap.addParty(operation);
                break;
            case EMPTY:
                coverageOperationMap.addEmpty(operation);
                break;
            case FULL:
                coverageOperationMap.addFull(operation);
                break;
        }
    }
}
