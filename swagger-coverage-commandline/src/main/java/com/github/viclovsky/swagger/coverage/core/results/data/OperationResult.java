package com.github.viclovsky.swagger.coverage.core.results.data;

import com.github.viclovsky.swagger.coverage.core.model.Condition;
import com.github.viclovsky.swagger.coverage.core.model.OperationKey;

import java.util.List;

public class OperationResult {

    protected OperationKey operationKey;
    protected List<Condition> conditions;
    protected long allBrancheCount;
    protected long coveredBrancheCount;
    protected long processCount;
    protected String description;
    protected CoverageState state;

    public OperationResult(List<Condition> conditions) {
        this.conditions = conditions;
        allBrancheCount = conditions.size();
        coveredBrancheCount = conditions.stream().filter(Condition::isCovered).count();

        if (coveredBrancheCount == 0){
            state = CoverageState.EMPTY;
        } else {
            if (allBrancheCount == coveredBrancheCount){
                state = CoverageState.FULL;
            } else {
                state = CoverageState.PARTY;
            }
        }
    }

    public List<Condition> getBranches() {
        return conditions;
    }

    public OperationResult setBranches(List<Condition> branches) {
        this.conditions = branches;
        return this;
    }

    public long getAllBrancheCount() {
        return allBrancheCount;
    }

    public OperationResult setAllBrancheCount(long allBrancheCount) {
        this.allBrancheCount = allBrancheCount;
        return this;
    }

    public long getCoveredBrancheCount() {
        return coveredBrancheCount;
    }

    public OperationResult setCoveredBrancheCount(long coveredBrancheCount) {
        this.coveredBrancheCount = coveredBrancheCount;
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
        if (description == null){
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
}
