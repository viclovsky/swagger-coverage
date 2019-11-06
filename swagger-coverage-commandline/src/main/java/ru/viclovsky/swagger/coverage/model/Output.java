package ru.viclovsky.swagger.coverage.model;

import java.util.Map;
import java.util.Set;

public class Output {
    private int allCount;
    private int emptyCount;
    private int partialCount;
    private int fullCount;
    private Set<String> empty;
    private Set<String> full;
    private Map<String, Problem> partial;

    public int getAllCount() {
        return allCount;
    }

    public Output withAllCount(int allCount) {
        this.allCount = allCount;
        return this;
    }

    public int getEmptyCount() {
        return emptyCount;
    }

    public Output withEmptyCount(int emptyCount) {
        this.emptyCount = emptyCount;
        return this;
    }

    public int getPartialCount() {
        return partialCount;
    }

    public Output withPartialCount(int partialCount) {
        this.partialCount = partialCount;
        return this;
    }

    public int getFullCount() {
        return fullCount;
    }

    public Output withFullCount(int fullCount) {
        this.fullCount = fullCount;
        return this;
    }

    public Set<String> getEmpty() {
        return empty;
    }

    public Output withEmpty(Set<String> empty) {
        this.empty = empty;
        return this;
    }

    public Set<String> getFull() {
        return full;
    }

    public Output withFull(Set<String> full) {
        this.full = full;
        return this;
    }

    public Map<String, Problem> getPartial() {
        return partial;
    }

    public Output withPartial(Map<String, Problem> partial) {
        this.partial = partial;
        return this;
    }
}
