package com.github.viclovsky.swagger.coverage.branch.results.builder.postbuilder;

import com.github.viclovsky.swagger.coverage.branch.results.Results;
import com.github.viclovsky.swagger.coverage.branch.results.builder.core.StatisticsOperationPostBuilder;
import com.github.viclovsky.swagger.coverage.branch.results.data.BranchCounter;
import com.github.viclovsky.swagger.coverage.branch.results.data.CoverageOperationMap;
import com.github.viclovsky.swagger.coverage.branch.results.data.OperationResult;
import com.github.viclovsky.swagger.coverage.branch.rule.core.BranchRule;
import io.swagger.models.Swagger;

import java.util.List;

public class BranchStatisticsBuilder extends StatisticsOperationPostBuilder {

    protected CoverageOperationMap coverageOperationMap = new CoverageOperationMap();
    protected BranchCounter branchCounter = new BranchCounter();

    public BranchStatisticsBuilder(Swagger swagger, List<BranchRule> rules) {
        super(swagger, rules);
    }

    @Override
    public void buildResult(Results results) {
        results
            .setCoverageOperationMap(coverageOperationMap)
            .setBranchCounter(branchCounter)
            ;
    }

    @Override
    public void buildOperation(String operation, OperationResult operationResult) {
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
