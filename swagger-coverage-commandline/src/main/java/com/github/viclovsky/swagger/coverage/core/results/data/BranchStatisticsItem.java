package com.github.viclovsky.swagger.coverage.core.results.data;

import com.github.viclovsky.swagger.coverage.core.model.Condition;
import com.github.viclovsky.swagger.coverage.core.model.OperationKey;

public class BranchStatisticsItem {

    protected OperationKey operation;
    protected Condition condition;

    public BranchStatisticsItem(OperationKey operation, Condition condition) {
        this.operation = operation;
        this.condition = condition;
    }

    public OperationKey getOperation() {
        return operation;
    }

    public BranchStatisticsItem setOperation(OperationKey operation) {
        this.operation = operation;
        return this;
    }

    public Condition getBranch() {
        return condition;
    }

    public BranchStatisticsItem setBranch(Condition condition) {
        this.condition = condition;
        return this;
    }
}
