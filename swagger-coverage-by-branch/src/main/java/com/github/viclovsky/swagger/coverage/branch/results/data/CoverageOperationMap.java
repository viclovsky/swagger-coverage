package com.github.viclovsky.swagger.coverage.branch.results.data;

import java.util.HashSet;
import java.util.Set;

public class CoverageOperationMap {
    protected Set<String> full = new HashSet<>();
    protected Set<String> party = new HashSet<>();
    protected Set<String> empty = new HashSet<>();

    protected CoverageCounter counter = new CoverageCounter();

    public CoverageOperationMap addFull(String operation){
        this.full.add(operation);
        this.counter.incrementFull();
        return this;
    }

    public CoverageOperationMap addParty(String operation){
        this.party.add(operation);
        this.counter.incrementParty();
        return this;
    }

    public CoverageOperationMap addEmpty(String operation){
        this.empty.add(operation);
        this.counter.incrementEmpty();
        return this;
    }

    public Set<String> getFull() {
        return full;
    }

    public CoverageOperationMap setFull(Set<String> full) {
        this.full = full;
        return this;
    }

    public Set<String> getParty() {
        return party;
    }

    public CoverageOperationMap setParty(Set<String> party) {
        this.party = party;
        return this;
    }

    public Set<String> getEmpty() {
        return empty;
    }

    public CoverageOperationMap setEmpty(Set<String> empty) {
        this.empty = empty;
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
