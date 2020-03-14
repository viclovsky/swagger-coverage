package com.github.viclovsky.swagger.coverage.branch.results;

import com.github.viclovsky.swagger.coverage.branch.model.Branch;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BranchStatistics {

    protected long allCount = 0;
    protected long coveredCount = 0;
    protected long uncoveredCount = 0;

    protected Map<String, Branch> coveredOperation = new HashMap<>();
    protected Map<String, Branch> uncoveredOperation = new HashMap<>();

    public long getAllCount() {
        return allCount;
    }

    public BranchStatistics setAllCount(long allCount) {
        this.allCount = allCount;
        return this;
    }

    public long getCoveredCount() {
        return coveredCount;
    }

    public BranchStatistics setCoveredCount(long coveredCount) {
        this.coveredCount = coveredCount;
        return this;
    }

    public long getUncoveredCount() {
        return uncoveredCount;
    }

    public BranchStatistics setUncoveredCount(long uncoveredCount) {
        this.uncoveredCount = uncoveredCount;
        return this;
    }

    public Map<String, Branch> getCoveredOperation() {
        return coveredOperation;
    }

    public BranchStatistics setCoveredOperation(Map<String, Branch> coveredOperation) {
        this.coveredOperation = coveredOperation;
        return this;
    }

    public Map<String, Branch> getUncoveredOperation() {
        return uncoveredOperation;
    }

    public BranchStatistics setUncoveredOperation(Map<String, Branch> uncoveredOperation) {
        this.uncoveredOperation = uncoveredOperation;
        return this;
    }

    public BranchStatistics processBranch(String operation, Branch branch){
        this.allCount++;

        if (branch.isCovered()){
            this.coveredCount++;
            this.coveredOperation.put(operation,branch);
        } else {
            this.uncoveredCount++;
            this.uncoveredOperation.put(operation,branch);;
        }

        return this;
    }
}
