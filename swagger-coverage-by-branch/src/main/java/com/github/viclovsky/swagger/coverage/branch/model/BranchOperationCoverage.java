package com.github.viclovsky.swagger.coverage.branch.model;

import io.swagger.models.Operation;

import java.util.List;

public class BranchOperationCoverage {

    protected Operation operation;
    protected List<Branch> branches;
    protected long processCount = 0;

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

    public long getProcessCount() {
        return processCount;
    }

    public BranchOperationCoverage setProcessCount(long processCount) {
        this.processCount = processCount;
        return this;
    }

    public BranchOperationCoverage increaseProcessCount(){
        this.processCount++;
        return this;
    }
}
