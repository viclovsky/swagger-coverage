package com.github.viclovsky.swagger.coverage.core.results;

import com.github.viclovsky.swagger.coverage.core.model.Condition;
import com.github.viclovsky.swagger.coverage.core.model.ConditionOperationCoverage;
import com.github.viclovsky.swagger.coverage.core.model.OperationKey;
import io.swagger.models.Info;
import io.swagger.models.Operation;

import java.util.Map;
import java.util.TreeMap;

public class Results {

    private Map<OperationKey, OperationResult> operations = new TreeMap<>();
    private Map<OperationKey, OperationResult> full = new TreeMap<>();
    private Map<OperationKey, OperationResult> party = new TreeMap<>();
    private Map<OperationKey, OperationResult> empty = new TreeMap<>();
    private Map<OperationKey, Operation> missed  = new TreeMap<>();
    private long allConditionCount;
    private long coveredConditionCount;
    private long allOperationCount;
    private long fullOperationCount;
    private long partOperationCount;
    private long emptyOperationCount;

    private GenerationStatistics generationStatistics;
    private Info info;

    public Results(Map<OperationKey, ConditionOperationCoverage> mainCoverageData) {
        mainCoverageData.forEach((key, value) -> {
            value.getConditions().forEach(Condition::postCheck);
            operations.put(key, new OperationResult(value.getConditions()));
        });

        allOperationCount = operations.size();

        operations.forEach((key, value) -> {
            allConditionCount = allConditionCount + value.getAllConditionCount();
            coveredConditionCount = coveredConditionCount + value.getCoveredConditionCount();

            if (value.getCoveredConditionCount() == 0) {
                emptyOperationCount++;
                empty.put(key, value);
            } else {
                if (value.getAllConditionCount() == value.getCoveredConditionCount()) {
                    fullOperationCount++;
                    full.put(key, value);
                } else {
                    partOperationCount++;
                    party.put(key, value);
                }
            }
        });

    }

    public Map<OperationKey, OperationResult> getOperations() {
        return operations;
    }

    public Results setOperations(Map<OperationKey, OperationResult> operations) {
        this.operations = operations;
        return this;
    }

    public long getAllConditionCount() {
        return allConditionCount;
    }

    public Results setAllConditionCount(long allConditionCount) {
        this.allConditionCount = allConditionCount;
        return this;
    }

    public long getCoveredConditionCount() {
        return coveredConditionCount;
    }

    public Results setCoveredConditionCount(long coveredConditionCount) {
        this.coveredConditionCount = coveredConditionCount;
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

    public Map<OperationKey, Operation> getMissed() {
        return missed;
    }

    public Results setMissed(Map<OperationKey, Operation> missed) {
        this.missed = missed;
        return this;
    }

    public Map<OperationKey, OperationResult> getFull() {
        return full;
    }

    public Results setFull(Map<OperationKey, OperationResult> full) {
        this.full = full;
        return this;
    }

    public Map<OperationKey, OperationResult> getParty() {
        return party;
    }

    public Results setParty(Map<OperationKey, OperationResult> party) {
        this.party = party;
        return this;
    }

    public Map<OperationKey, OperationResult> getEmpty() {
        return empty;
    }

    public Results setEmpty(Map<OperationKey, OperationResult> empty) {
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
