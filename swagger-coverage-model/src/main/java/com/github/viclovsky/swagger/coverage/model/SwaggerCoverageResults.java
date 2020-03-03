package com.github.viclovsky.swagger.coverage.model;

import java.util.Map;

public class SwaggerCoverageResults {
    private String title;
    private String version;
    private Map<String, Coverage> emptyCoverage;
    private Map<String, Coverage> fullCoverage;
    private Map<String, Coverage> partialCoverage;
    private Map<String, Coverage> missedCoverage;
    private Statistics statistics;

    public Map<String, Coverage> getEmptyCoverage() {
        return emptyCoverage;
    }

    public SwaggerCoverageResults setEmptyCoverage(Map<String, Coverage> emptyCoverage) {
        this.emptyCoverage = emptyCoverage;
        return this;
    }

    public Map<String, Coverage> getFullCoverage() {
        return fullCoverage;
    }

    public SwaggerCoverageResults setFullCoverage(Map<String, Coverage> fullCoverage) {
        this.fullCoverage = fullCoverage;
        return this;
    }

    public Map<String, Coverage> getPartialCoverage() {
        return partialCoverage;
    }

    public SwaggerCoverageResults setPartialCoverage(Map<String, Coverage> partialCoverage) {
        this.partialCoverage = partialCoverage;
        return this;
    }

    public Map<String, Coverage> getMissedCoverage() {
        return missedCoverage;
    }

    public SwaggerCoverageResults setMissedCoverage(Map<String, Coverage> missedCoverage) {
        this.missedCoverage = missedCoverage;
        return this;
    }

    public SwaggerCoverageResults setStatistics(Statistics statistics) {
        this.statistics = statistics;
        return this;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public String getTitle() {
        return title;
    }

    public SwaggerCoverageResults setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public SwaggerCoverageResults setVersion(String version) {
        this.version = version;
        return this;
    }
}
