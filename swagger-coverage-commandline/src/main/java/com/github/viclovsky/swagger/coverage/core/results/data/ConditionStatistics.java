package com.github.viclovsky.swagger.coverage.core.results.data;

import com.github.viclovsky.swagger.coverage.core.model.Condition;
import com.github.viclovsky.swagger.coverage.core.model.OperationKey;

import java.util.ArrayList;
import java.util.List;

public class ConditionStatistics {

    private long allCount = 0;
    private long coveredCount = 0;
    private long uncoveredCount = 0;

    private List<ConditionStatisticsItem> coveredOperation = new ArrayList<>();
    private List<ConditionStatisticsItem> uncoveredOperation = new ArrayList<>();

    public long getAllCount() {
        return allCount;
    }

    public ConditionStatistics setAllCount(long allCount) {
        this.allCount = allCount;
        return this;
    }

    public long getCoveredCount() {
        return coveredCount;
    }

    public ConditionStatistics setCoveredCount(long coveredCount) {
        this.coveredCount = coveredCount;
        return this;
    }

    public long getUncoveredCount() {
        return uncoveredCount;
    }

    public ConditionStatistics setUncoveredCount(long uncoveredCount) {
        this.uncoveredCount = uncoveredCount;
        return this;
    }

    public List<ConditionStatisticsItem> getCoveredOperation() {
        return coveredOperation;
    }

    public ConditionStatistics setCoveredOperation(List<ConditionStatisticsItem> coveredOperation) {
        this.coveredOperation = coveredOperation;
        return this;
    }

    public List<ConditionStatisticsItem> getUncoveredOperation() {
        return uncoveredOperation;
    }

    public ConditionStatistics setUncoveredOperation(List<ConditionStatisticsItem> uncoveredOperation) {
        this.uncoveredOperation = uncoveredOperation;
        return this;
    }

    public ConditionStatistics processCondition(OperationKey operation, Condition condition) {
        this.allCount++;

        if (condition.isCovered()) {
            this.coveredCount++;
            this.coveredOperation.add(new ConditionStatisticsItem(operation, condition));
        } else {
            this.uncoveredCount++;
            this.uncoveredOperation.add(new ConditionStatisticsItem(operation, condition));
            ;
        }

        return this;
    }
}
