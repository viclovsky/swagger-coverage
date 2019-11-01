package ru.viclovsky.swagger.coverage;

import io.swagger.models.Operation;

import java.util.Map;

public class Output {

    private Output() {
    }

    private Map<String, Operation> emptyCoverage;
    private Map<String, Operation> partialCoverage;
    private Map<String, Operation> fullCoverage;

    public Map<String, Operation> getEmptyCoverage() {
        return emptyCoverage;
    }

    public Output withEmptyCoverage(Map<String, Operation> emptyCoverage) {
        this.emptyCoverage = emptyCoverage;
        return this;
    }

    public Map<String, Operation> getPartialCoverage() {
        return partialCoverage;
    }

    public Output withPartialCoverage(Map<String, Operation> partialCoverage) {
        this.partialCoverage = partialCoverage;
        return this;
    }

    public Map<String, Operation> getFullCoverage() {
        return fullCoverage;
    }

    public Output withFullCoverage(Map<String, Operation> fullCoverage) {
        this.fullCoverage = fullCoverage;
        return this;
    }

    public static Output output() {
        return new Output();
    }
}
