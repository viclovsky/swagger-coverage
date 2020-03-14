package com.github.viclovsky.swagger.coverage.branch.results.data;

import com.github.viclovsky.swagger.coverage.branch.model.Branch;

import java.util.List;

public class OperationResult {

    protected List<Branch> branches;
    protected long allBrancheCount;
    protected long coveredBrancheCount;
    protected long processCount;
    protected String description;
    protected CoverageState state;

    public OperationResult(List<Branch> branches) {
        this.branches = branches;
        allBrancheCount = branches.size();
        coveredBrancheCount = branches.stream().filter(Branch::isCovered).count();

        if (coveredBrancheCount == 0){
            state = CoverageState.EMPTY;
        } else {
            if (allBrancheCount == coveredBrancheCount){
                state = CoverageState.FULL;
            } else {
                state = CoverageState.PARTY;
            }
        }
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

    public String getDescription() {
        if (description == null){
            return "";
        }
        return description;
    }

    public OperationResult setDescription(String description) {
        this.description = description;
        return this;
    }

    public CoverageState getState() {
        return state;
    }

    public OperationResult setState(CoverageState state) {
        this.state = state;
        return this;
    }
}
