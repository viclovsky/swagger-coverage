package com.github.viclovsky.swagger.coverage.core.results.data;

import com.github.viclovsky.swagger.coverage.core.model.OperationKey;

import java.util.HashSet;
import java.util.Set;

public class CoverageOperationMap {
    private Set<OperationKey> full = new HashSet<>();
    private Set<OperationKey> party = new HashSet<>();
    private Set<OperationKey> empty = new HashSet<>();
    private Set<OperationKey> deprecated = new HashSet<>();

    protected CoverageCounter counter = new CoverageCounter();

    public CoverageOperationMap addFull(OperationKey operation) {
        this.full.add(operation);
        this.counter.incrementFull();
        return this;
    }

    public CoverageOperationMap addParty(OperationKey operation) {
        this.party.add(operation);
        this.counter.incrementParty();
        return this;
    }

    public CoverageOperationMap addEmpty(OperationKey operation) {
        this.empty.add(operation);
        this.counter.incrementEmpty();
        return this;
    }

    public CoverageOperationMap addDeprecated(OperationKey operation) {
        this.deprecated.add(operation);
        this.counter.incrementDeprecated();
        return this;
    }

    public Set<OperationKey> getFull() {
        return full;
    }

    public CoverageOperationMap setFull(Set<OperationKey> full) {
        this.full = full;
        return this;
    }

    public Set<OperationKey> getParty() {
        return party;
    }

    public CoverageOperationMap setParty(Set<OperationKey> party) {
        this.party = party;
        return this;
    }

    public Set<OperationKey> getEmpty() {
        return empty;
    }

    public CoverageOperationMap setEmpty(Set<OperationKey> empty) {
        this.empty = empty;
        return this;
    }

    public Set<OperationKey> getDeprecated() {
        return deprecated;
    }

    public CoverageOperationMap setDeprecated(Set<OperationKey> deprecated) {
        this.deprecated = deprecated;
        return this;
    }

    public CoverageCounter getCounter() {
        return counter;
    }

    public CoverageOperationMap setCounter(CoverageCounter counter) {
        this.counter = counter;
        return this;
    }
}
