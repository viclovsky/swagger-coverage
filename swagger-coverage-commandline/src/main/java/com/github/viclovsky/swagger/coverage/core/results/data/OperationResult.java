package com.github.viclovsky.swagger.coverage.core.results.data;

import com.github.viclovsky.swagger.coverage.core.model.Condition;
import com.github.viclovsky.swagger.coverage.core.model.OperationKey;

import java.util.List;

public class OperationResult {

    private OperationKey operationKey;
    private List<Condition> conditions;
    private long allConditionCount;
    private long coveredConditionCount;
    private long processCount;
    private String description;
    private CoverageState state;
    private boolean deprecated;

    public OperationResult(List<Condition> conditions, Boolean isDeprecated) {
        this.conditions = conditions;
        this.deprecated = (isDeprecated != null) ? isDeprecated : false;
        allConditionCount = conditions.size();
        coveredConditionCount = conditions.stream().filter(Condition::isCovered).count();

        if (coveredConditionCount == 0) {
            state = CoverageState.EMPTY;
        } else {
            if (allConditionCount == coveredConditionCount) {
                state = CoverageState.FULL;
            } else {
                state = CoverageState.PARTY;
            }
        }
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

    public long getProcessCount() {
        return processCount;
    }

    public OperationResult setProcessCount(long processCount) {
        this.processCount = processCount;
        return this;
    }

    public String getDescription() {
        if (description == null) {
            return "";
        }
        return description;
    }

    public OperationResult setDescription(String description) {
        this.description = description;
        return this;
    }

    public CoverageState getState() {
        return state;
    }

    public OperationResult setState(CoverageState state) {
        this.state = state;
        return this;
    }

    public OperationKey getOperationKey() {
        return operationKey;
    }

    public OperationResult setOperationKey(OperationKey operationKey) {
        this.operationKey = operationKey;
        return this;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public boolean getDeprecated() {
        return deprecated;
    }

    public void setDeprecated(boolean isDeprecated) {
        this.deprecated = isDeprecated;
    }
}
