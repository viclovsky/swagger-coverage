package com.github.viclovsky.swagger.coverage.branch.results;

import com.github.viclovsky.swagger.coverage.branch.model.Branch;

import java.util.List;

public class OperationResult {

    protected List<Branch> branches;
    protected long allBrancheCount;
    protected long coveredBrancheCount;
    protected long processCount;

    public OperationResult(List<Branch> branches) {
        this.branches = branches;
        allBrancheCount = branches.size();
        coveredBrancheCount = branches.stream().filter(Branch::isCovered).count();
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public OperationResult setBranches(List<Branch> branches) {
        this.branches = branches;
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
}
