package com.github.viclovsky.swagger.coverage.core.results;

import com.github.viclovsky.swagger.coverage.core.model.OperationKey;
import com.github.viclovsky.swagger.coverage.core.results.data.*;
import io.swagger.models.Operation;

import java.util.*;

public class Results {

    protected Map<OperationKey, OperationResult> operations = new TreeMap<>();
    protected Map<String, OperationResult> flatOperations = new TreeMap<>();
    protected Map<OperationKey, Operation> missed  = new TreeMap<>();
    protected Map<String, BranchStatistics> branchStatisticsMap = new HashMap<>();
    protected Set<OperationKey> zeroCall  = new HashSet<>();

    protected GenerationStatistics generationStatistics;
    protected CoverageOperationMap coverageOperationMap = new CoverageOperationMap();
    protected BranchCounter branchCounter = new BranchCounter();

    /** TAG STATISTICS **/
    protected Map<String, TagCoverage> tagCoverageMap = new TreeMap<>();
    protected CoverageCounter tagCounter;

    protected String prettyConfiguration;

    public Results(){

    }

    public Map<OperationKey, OperationResult> getOperations() {
        return operations;
    }

    public Results setOperations(Map<OperationKey, OperationResult> operations) {
        this.operations = operations;
        return this;
    }

    public Set<OperationKey> getZeroCall() {
        return zeroCall;
    }

    public Results setZeroCall(Set<OperationKey> zeroCall) {
        this.zeroCall = zeroCall;
        return this;
    }

    public Map<OperationKey, Operation> getMissed() {
        return missed;
    }

    public Results setMissed(Map<OperationKey, Operation> missed) {
        this.missed = missed;
        return this;
    }

    public BranchCounter getBranchCounter() {
        return branchCounter;
    }

    public Results setBranchCounter(BranchCounter branchCounter) {
        this.branchCounter = branchCounter;
        return this;
    }

    public GenerationStatistics getGenerationStatistics() {
        return generationStatistics;
    }

    public Results setGenerationStatistics(GenerationStatistics generationStatistics) {
        this.generationStatistics = generationStatistics;
        return this;
    }

    public Map<String, BranchStatistics> getBranchStatisticsMap() {
        return branchStatisticsMap;
    }

    public Results setBranchStatisticsMap(Map<String, BranchStatistics> branchStatisticsMap) {
        this.branchStatisticsMap = branchStatisticsMap;
        return this;
    }

    public CoverageOperationMap getCoverageOperationMap() {
        return coverageOperationMap;
    }

    public Results setCoverageOperationMap(CoverageOperationMap coverageOperationMap) {
        this.coverageOperationMap = coverageOperationMap;
        return this;
    }

    public Map<String, TagCoverage> getTagCoverageMap() {
        return tagCoverageMap;
    }

    public Results setTagCoverageMap(Map<String, TagCoverage> tagCoverageMap) {
        this.tagCoverageMap = tagCoverageMap;
        return this;
    }

    public CoverageCounter getTagCounter() {
        return tagCounter;
    }

    public Results setTagCounter(CoverageCounter tagCounter) {
        this.tagCounter = tagCounter;
        return this;
    }

    public String getPrettyConfiguration() {
        return prettyConfiguration;
    }

    public Results setPrettyConfiguration(String prettyConfiguration) {
        this.prettyConfiguration = prettyConfiguration;
        return this;
    }

    public Map<String, OperationResult> getFlatOperations() {
        return flatOperations;
    }

    public Results setFlatOperations(Map<String, OperationResult> flatOperations) {
        this.flatOperations = flatOperations;
        return this;
    }
}
