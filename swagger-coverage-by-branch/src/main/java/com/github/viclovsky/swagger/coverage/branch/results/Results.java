package com.github.viclovsky.swagger.coverage.branch.results;

import com.github.viclovsky.swagger.coverage.branch.model.BranchOperationCoverage;
import io.swagger.models.Operation;

import java.util.Map;
import java.util.TreeMap;

public class Results {

    protected Map<String, OperationResult> operations = new TreeMap<>();
    protected Map<String, OperationResult> full = new TreeMap<>();
    protected Map<String, OperationResult> party = new TreeMap<>();
    protected Map<String, OperationResult> empty = new TreeMap<>();
    protected Map<String, Operation> missed  = new TreeMap<>();
    protected long allBrancheCount;
    protected long coveredBrancheCount;
    protected long allOperationCount;
    protected long fullOperationCount;
    protected long partOperationCount;
    protected long emptyOperationCount;

    protected GenerationStatistics generationStatistics;

    public Results(Map<String, BranchOperationCoverage> mainCoverageData){
        mainCoverageData.entrySet().stream().forEach(entry -> {
            entry.getValue().getBranches().stream().forEach(branch -> branch.postCheck());

            operations.put(
                entry.getKey(),
                new OperationResult(entry.getValue().getBranches())
            );
        });

        allOperationCount = operations.size();

        operations.entrySet().forEach(entry -> {
            allBrancheCount = allBrancheCount + entry.getValue().getAllBrancheCount();
            coveredBrancheCount = coveredBrancheCount + entry.getValue().getCoveredBrancheCount();

            if (entry.getValue().getCoveredBrancheCount() == 0) {
                emptyOperationCount++;
                empty.put(entry.getKey(),entry.getValue());
            } else {
                if (entry.getValue().getAllBrancheCount() == entry.getValue().getCoveredBrancheCount()) {
                    fullOperationCount++;
                    full.put(entry.getKey(),entry.getValue());
                } else {
                    partOperationCount++;
                    party.put(entry.getKey(),entry.getValue());
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

    public long getAllBrancheCount() {
        return allBrancheCount;
    }

    public Results setAllBrancheCount(long allBrancheCount) {
        this.allBrancheCount = allBrancheCount;
        return this;
    }

    public long getCoveredBrancheCount() {
        return coveredBrancheCount;
    }

    public Results setCoveredBrancheCount(long coveredBrancheCount) {
        this.coveredBrancheCount = coveredBrancheCount;
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
}
