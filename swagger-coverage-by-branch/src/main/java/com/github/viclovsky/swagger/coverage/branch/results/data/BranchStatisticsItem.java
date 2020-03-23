package com.github.viclovsky.swagger.coverage.branch.results.data;

import com.github.viclovsky.swagger.coverage.branch.model.Branch;

public class BranchStatisticsItem {

    protected String operation;
    protected Branch branch;

    public BranchStatisticsItem(String operation, Branch branch) {
        this.operation = operation;
        this.branch = branch;
    }

    public String getOperation() {
        return operation;
    }

    public BranchStatisticsItem setOperation(String operation) {
        this.operation = operation;
        return this;
    }

    public Branch getBranch() {
        return branch;
    }

    public BranchStatisticsItem setBranch(Branch branch) {
        this.branch = branch;
        return this;
    }
}
