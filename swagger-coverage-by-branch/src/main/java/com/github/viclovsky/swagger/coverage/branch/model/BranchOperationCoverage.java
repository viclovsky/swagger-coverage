package com.github.viclovsky.swagger.coverage.branch.model;

import io.swagger.models.Operation;

import java.util.List;

public class BranchOperationCoverage {

    private Operation operation;
    private List<Branch> branches;

    public Operation getOperation() {
        return operation;
    }

    public BranchOperationCoverage setOperation(Operation operation) {
        this.operation = operation;
        return this;
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public BranchOperationCoverage setBranches(List<Branch> branches) {
        this.branches = branches;
        return this;
    }
}
