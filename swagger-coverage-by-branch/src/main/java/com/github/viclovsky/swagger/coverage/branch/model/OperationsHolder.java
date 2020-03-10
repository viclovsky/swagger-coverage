package com.github.viclovsky.swagger.coverage.branch.model;

import io.swagger.models.Operation;

import java.util.Map;
import java.util.TreeMap;

public class OperationsHolder {

    Map<String, Operation> operations = new TreeMap<>();

    public Map<String, Operation> getOperations() {
        return operations;
    }

    public OperationsHolder setOperations(Map<String, Operation> operations) {
        this.operations = operations;
        return this;
    }

    public OperationsHolder addOperation(String key, Operation operation) {
        this.operations.put(key,operation);
        return this;
    }
}
