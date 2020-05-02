package com.github.viclovsky.swagger.coverage.core.results;

import com.github.viclovsky.swagger.coverage.core.model.OperationKey;
import com.github.viclovsky.swagger.coverage.core.results.data.ConditionCounter;
import com.github.viclovsky.swagger.coverage.core.results.data.ConditionStatistics;
import com.github.viclovsky.swagger.coverage.core.results.data.CoverageCounter;
import com.github.viclovsky.swagger.coverage.core.results.data.CoverageOperationMap;
import com.github.viclovsky.swagger.coverage.core.results.data.GenerationStatistics;
import com.github.viclovsky.swagger.coverage.core.results.data.OperationResult;
import com.github.viclovsky.swagger.coverage.core.results.data.TagCoverage;
import io.swagger.models.Info;
import io.swagger.models.Operation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Results {

    private Map<OperationKey, OperationResult> operations = new TreeMap<>();
    private Map<String, OperationResult> flatOperations = new TreeMap<>();
    private Map<OperationKey, Operation> missed = new TreeMap<>();
    private Map<String, ConditionStatistics> conditionStatisticsMap = new HashMap<>();
    private Set<OperationKey> zeroCall = new HashSet<>();

    private GenerationStatistics generationStatistics;
    private CoverageOperationMap coverageOperationMap = new CoverageOperationMap();
    private ConditionCounter conditionCounter = new ConditionCounter();

    /**
     * TAG STATISTICS
     **/
    private Map<String, TagCoverage> tagCoverageMap = new TreeMap<>();
    private CoverageCounter tagCounter;

    /**
     *
     **/
    private String prettyConfiguration;
    private Info info;

    public Results() {

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

    public Info getInfo() {
        return info;
    }

    public Results setInfo(Info info) {
        this.info = info;
        return this;
    }
}
