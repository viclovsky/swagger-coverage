package ru.viclovsky.swagger.coverage.model;

import java.util.Map;
import java.util.Set;

public class Output {
    private Set<String> empty;
    private Set<String> full;
    private Map<String, Problem> partial;
    private Statistics statistics;

    public Set<String> getEmpty() {
        return empty;
    }

    public Output setEmpty(Set<String> empty) {
        this.empty = empty;
        return this;
    }

    public Set<String> getFull() {
        return full;
    }

    public Output setFull(Set<String> full) {
        this.full = full;
        return this;
    }

    public Map<String, Problem> getPartial() {
        return partial;
    }

    public Output setPartial(Map<String, Problem> partial) {
        this.partial = partial;
        return this;
    }

    public Output setStatistics(Statistics statistics) {
        this.statistics = statistics;
        return this;
    }

    public Statistics getStatistics() {
        return statistics;
    }
}
