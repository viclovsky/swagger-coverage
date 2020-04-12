package com.github.viclovsky.swagger.coverage.core.results.data;

import com.github.viclovsky.swagger.coverage.core.model.Condition;
import com.github.viclovsky.swagger.coverage.core.model.OperationKey;

public class ConditionStatisticsItem {

    protected OperationKey operation;
    protected Condition condition;

    public ConditionStatisticsItem(OperationKey operation, Condition condition) {
        this.operation = operation;
        this.condition = condition;
    }

    public OperationKey getOperation() {
        return operation;
    }

    public ConditionStatisticsItem setOperation(OperationKey operation) {
        this.operation = operation;
        return this;
    }

    public Condition getCondition() {
        return condition;
    }

    public ConditionStatisticsItem setCondition(Condition condition) {
        this.condition = condition;
        return this;
    }
}
