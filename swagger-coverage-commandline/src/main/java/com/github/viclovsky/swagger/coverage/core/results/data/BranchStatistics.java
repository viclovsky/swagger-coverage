package com.github.viclovsky.swagger.coverage.core.results.data;

import com.github.viclovsky.swagger.coverage.core.model.Condition;
import com.github.viclovsky.swagger.coverage.core.model.OperationKey;

import java.util.ArrayList;
import java.util.List;

public class BranchStatistics {

    protected long allCount = 0;
    protected long coveredCount = 0;
    protected long uncoveredCount = 0;

    protected List<BranchStatisticsItem> coveredOperation = new ArrayList<>();
    protected List<BranchStatisticsItem> uncoveredOperation = new ArrayList<>();

    public long getAllCount() {
        return allCount;
    }

    public BranchStatistics setAllCount(long allCount) {
        this.allCount = allCount;
        return this;
    }

    public long getCoveredCount() {
        return coveredCount;
    }

    public BranchStatistics setCoveredCount(long coveredCount) {
        this.coveredCount = coveredCount;
        return this;
    }

    public long getUncoveredCount() {
        return uncoveredCount;
    }

    public BranchStatistics setUncoveredCount(long uncoveredCount) {
        this.uncoveredCount = uncoveredCount;
        return this;
    }

    public List<BranchStatisticsItem> getCoveredOperation() {
        return coveredOperation;
    }

    public BranchStatistics setCoveredOperation(List<BranchStatisticsItem> coveredOperation) {
        this.coveredOperation = coveredOperation;
        return this;
    }

    public List<BranchStatisticsItem> getUncoveredOperation() {
        return uncoveredOperation;
    }

    public BranchStatistics setUncoveredOperation(List<BranchStatisticsItem> uncoveredOperation) {
        this.uncoveredOperation = uncoveredOperation;
        return this;
    }

    public BranchStatistics processBranch(OperationKey operation, Condition condition){
        this.allCount++;

        if (condition.isCovered()){
            this.coveredCount++;
            this.coveredOperation.add(new BranchStatisticsItem(operation, condition));
        } else {
            this.uncoveredCount++;
            this.uncoveredOperation.add(new BranchStatisticsItem(operation, condition));;
        }

        return this;
    }
}
