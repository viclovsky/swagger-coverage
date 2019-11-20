package ru.viclovsky.swagger.coverage.model;

import java.util.Map;
import java.util.Set;

public class SwaggerCoverageResults {
    private Set<String> emptyCoverage;
    private Set<String> fullCoverage;
    private Map<String, OperationCoverage> partialCoverage;
    private Statistics statistics;

    public Set<String> getEmptyCoverage() {
        return emptyCoverage;
    }

    public SwaggerCoverageResults setEmptyCoverage(Set<String> emptyCoverage) {
        this.emptyCoverage = emptyCoverage;
        return this;
    }

    public Set<String> getFullCoverage() {
        return fullCoverage;
    }

    public SwaggerCoverageResults setFullCoverage(Set<String> fullCoverage) {
        this.fullCoverage = fullCoverage;
        return this;
    }

    public Map<String, OperationCoverage> getPartialCoverage() {
        return partialCoverage;
    }

    public SwaggerCoverageResults setPartialCoverage(Map<String, OperationCoverage> partialCoverage) {
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
