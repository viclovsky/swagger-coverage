package com.github.viclovsky.swagger.coverage.core.results;

import com.github.viclovsky.swagger.coverage.core.model.Condition;

import java.util.List;

public class OperationResult {

    private List<Condition> conditions;
    private long allConditionCount;
    private long coveredConditionCount;

    public OperationResult(List<Condition> conditions) {
        this.conditions = conditions;
        allConditionCount = conditions.size();
        coveredConditionCount = conditions.stream().filter(Condition::isCovered).count();
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public OperationResult setConditions(List<Condition> conditions) {
        this.conditions = conditions;
        return this;
    }

    public long getAllConditionCount() {
        return allConditionCount;
    }

    public OperationResult setAllConditionCount(long allConditionCount) {
        this.allConditionCount = allConditionCount;
        return this;
    }

    public long getCoveredConditionCount() {
        return coveredConditionCount;
    }

    public OperationResult setCoveredConditionCount(long coveredConditionCount) {
        this.coveredConditionCount = coveredConditionCount;
        return this;
    }
}
