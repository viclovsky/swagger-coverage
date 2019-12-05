package ru.viclovsky.swagger.coverage.model;

import java.util.Map;
import java.util.Set;

public class SwaggerCoverageResults {
    private Set<String> emptyCoverage;
    private Map<String, Coverage> fullCoverage;
    private Map<String, Coverage> partialCoverage;
    private Statistics statistics;

    public Set<String> getEmptyCoverage() {
        return emptyCoverage;
    }

    public SwaggerCoverageResults setEmptyCoverage(Set<String> emptyCoverage) {
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

    public SwaggerCoverageResults setStatistics(Statistics statistics) {
        this.statistics = statistics;
        return this;
    }

    public Statistics getStatistics() {
        return statistics;
    }
}
