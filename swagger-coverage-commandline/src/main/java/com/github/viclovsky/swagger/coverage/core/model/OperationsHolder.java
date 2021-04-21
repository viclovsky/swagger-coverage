package com.github.viclovsky.swagger.coverage.core.model;

import io.swagger.v3.oas.models.Operation;

import java.util.Map;
import java.util.TreeMap;

public class OperationsHolder {

    private Map<OperationKey, Operation> operations = new TreeMap<>();

    public Map<OperationKey, Operation> getOperations() {
        return operations;
    }

    public OperationsHolder setOperations(Map<OperationKey, Operation> operations) {
        this.operations = operations;
        return this;
    }

    public OperationsHolder addOperation(OperationKey operationKey, Operation operation) {
        this.operations.put(operationKey, operation);
        return this;
    }
}
