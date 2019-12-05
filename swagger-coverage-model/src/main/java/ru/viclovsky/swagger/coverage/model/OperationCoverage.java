package ru.viclovsky.swagger.coverage.model;

import io.swagger.models.Operation;

public class OperationCoverage {

    private Coverage coverage;
    private Operation operation;

    public OperationCoverage(Operation operation) {
        this.operation = operation;
        this.coverage = new Coverage();
    }

    public OperationCoverage addCoveredStatusCode(String status) {
        coverage.getCoveredStatusCodes().add(status);
        return this;
    }

    public OperationCoverage addCoveredParameter(String parameter) {
        coverage.getCoveredParams().add(parameter);
        return this;
    }

    public OperationCoverage addIgnoredStatusCode(String status) {
        coverage.getIgnoredStatusCodes().add(status);
        return this;
    }

    public OperationCoverage addIgnoredParameter(String parameter) {
        coverage.getIgnoredParams().add(parameter);
        return this;
    }

    public Coverage getCoverage() {
        return coverage;
    }

    public Operation getOperation() {
        return operation;
    }

    public OperationCoverage setOperation(Operation operation) {
        this.operation = operation;
        return this;
    }
}
