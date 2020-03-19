package com.github.viclovsky.swagger.coverage.branch.results;

import com.github.viclovsky.swagger.coverage.branch.model.Branch;
import com.github.viclovsky.swagger.coverage.branch.model.BranchOperationCoverage;
import io.swagger.models.Info;
import io.swagger.models.Operation;

import java.util.Map;
import java.util.TreeMap;

public class Results {

    private Map<String, OperationResult> operations = new TreeMap<>();
    private Map<String, OperationResult> full = new TreeMap<>();
    private Map<String, OperationResult> party = new TreeMap<>();
    private Map<String, OperationResult> empty = new TreeMap<>();
    private Map<String, Operation> missed  = new TreeMap<>();
    private long allBranchCount;
    private long coveredBranchCount;
    private long allOperationCount;
    private long fullOperationCount;
    private long partOperationCount;
    private long emptyOperationCount;

    private GenerationStatistics generationStatistics;
    private Info info;

    public Results(Map<String, BranchOperationCoverage> mainCoverageData){
        mainCoverageData.forEach((key, value) -> {
            value.getBranches().forEach(Branch::postCheck);
            operations.put(key, new OperationResult(value.getBranches()));
        });

        allOperationCount = operations.size();

        operations.forEach((key, value) -> {
            allBranchCount = allBranchCount + value.getAllBranchCount();
            coveredBranchCount = coveredBranchCount + value.getCoveredBranchCount();

            if (value.getCoveredBranchCount() == 0) {
                emptyOperationCount++;
                empty.put(key, value);
            } else {
                if (value.getAllBranchCount() == value.getCoveredBranchCount()) {
                    fullOperationCount++;
                    full.put(key, value);
                } else {
                    partOperationCount++;
                    party.put(key, value);
                }
            }
        });

    }

    public Map<String, OperationResult> getOperations() {
        return operations;
    }

    public Results setOperations(Map<String, OperationResult> operations) {
        this.operations = operations;
        return this;
    }

    public long getAllBranchCount() {
        return allBranchCount;
    }

    public Results setAllBranchCount(long allBranchCount) {
        this.allBranchCount = allBranchCount;
        return this;
    }

    public long getCoveredBranchCount() {
        return coveredBranchCount;
    }

    public Results setCoveredBranchCount(long coveredBranchCount) {
        this.coveredBranchCount = coveredBranchCount;
        return this;
    }

    public long getAllOperationCount() {
        return allOperationCount;
    }

    public Results setAllOperationCount(long allOperationCount) {
        this.allOperationCount = allOperationCount;
        return this;
    }

    public long getFullOperationCount() {
        return fullOperationCount;
    }

    public Results setFullOperationCount(long fullOperationCount) {
        this.fullOperationCount = fullOperationCount;
        return this;
    }

    public long getPartOperationCount() {
        return partOperationCount;
    }

    public Results setPartOperationCount(long partOperationCount) {
        this.partOperationCount = partOperationCount;
        return this;
    }

    public long getEmptyOperationCount() {
        return emptyOperationCount;
    }

    public Results setEmptyOperationCount(long emptyOperationCount) {
        this.emptyOperationCount = emptyOperationCount;
        return this;
    }

    public Map<String, Operation> getMissed() {
        return missed;
    }

    public Results setMissed(Map<String, Operation> missed) {
        this.missed = missed;
        return this;
    }

    public Map<String, OperationResult> getFull() {
        return full;
    }

    public Results setFull(Map<String, OperationResult> full) {
        this.full = full;
        return this;
    }

    public Map<String, OperationResult> getParty() {
        return party;
    }

    public Results setParty(Map<String, OperationResult> party) {
        this.party = party;
        return this;
    }

    public Map<String, OperationResult> getEmpty() {
        return empty;
    }

    public Results setEmpty(Map<String, OperationResult> empty) {
        this.empty = empty;
        return this;
    }

    public GenerationStatistics getGenerationStatistics() {
        return generationStatistics;
    }

    public Results setGenerationStatistics(GenerationStatistics generationStatistics) {
        this.generationStatistics = generationStatistics;
        return this;
    }

    public Info getInfo() {
        return info;
    }

    public Results setInfo(Info info) {
        this.info = info;
        return this;
    }
}
