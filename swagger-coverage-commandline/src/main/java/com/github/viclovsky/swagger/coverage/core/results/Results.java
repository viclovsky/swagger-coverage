package com.github.viclovsky.swagger.coverage.core.results;

import com.github.viclovsky.swagger.coverage.core.model.OperationKey;
import com.github.viclovsky.swagger.coverage.core.results.data.*;
import io.swagger.models.Operation;

import java.util.*;

public class Results {

    protected Map<OperationKey, OperationResult> operations = new TreeMap<>();
    protected Map<String, OperationResult> flatOperations = new TreeMap<>();
    protected Map<OperationKey, Operation> missed  = new TreeMap<>();
    protected Map<String, ConditionStatistics> conditionStatisticsMap = new HashMap<>();
    protected Set<OperationKey> zeroCall  = new HashSet<>();

    protected GenerationStatistics generationStatistics;
    protected CoverageOperationMap coverageOperationMap = new CoverageOperationMap();
    protected ConditionCounter conditionCounter = new ConditionCounter();

    /** TAG STATISTICS **/
    protected Map<String, TagCoverage> tagCoverageMap = new TreeMap<>();
    protected CoverageCounter tagCounter;

    /** **/
    protected String prettyConfiguration;
    protected ViewData viewData;

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

    public GenerationStatistics getGenerationStatistics() {
        return generationStatistics;
    }

    public Results setGenerationStatistics(GenerationStatistics generationStatistics) {
        this.generationStatistics = generationStatistics;
        return this;
    }

    public ConditionCounter getConditionCounter() {
        return conditionCounter;
    }

    public Results setConditionCounter(ConditionCounter conditionCounter) {
        this.conditionCounter = conditionCounter;
        return this;
    }

    public Map<String, ConditionStatistics> getConditionStatisticsMap() {
        return conditionStatisticsMap;
    }

    public Results setConditionStatisticsMap(Map<String, ConditionStatistics> conditionStatisticsMap) {
        this.conditionStatisticsMap = conditionStatisticsMap;
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

    public ViewData getViewData() {
        return viewData;
    }

    public Results setViewData(ViewData viewData) {
        this.viewData = viewData;
        return this;
    }
}
