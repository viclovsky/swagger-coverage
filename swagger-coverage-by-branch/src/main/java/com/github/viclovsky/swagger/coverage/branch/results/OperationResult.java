package com.github.viclovsky.swagger.coverage.branch.results;

import com.github.viclovsky.swagger.coverage.branch.model.Branch;

import java.util.List;

public class OperationResult {

    private List<Branch> branches;
    private long allBranchCount;
    private long coveredBranchCount;

    public OperationResult(List<Branch> branches) {
        this.branches = branches;
        allBranchCount = branches.size();
        coveredBranchCount = branches.stream().filter(Branch::isCovered).count();
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public OperationResult setBranches(List<Branch> branches) {
        this.branches = branches;
        return this;
    }

    public long getAllBranchCount() {
        return allBranchCount;
    }

    public OperationResult setAllBranchCount(long allBranchCount) {
        this.allBranchCount = allBranchCount;
        return this;
    }

    public long getCoveredBranchCount() {
        return coveredBranchCount;
    }

    public OperationResult setCoveredBranchCount(long coveredBranchCount) {
        this.coveredBranchCount = coveredBranchCount;
        return this;
    }
}
