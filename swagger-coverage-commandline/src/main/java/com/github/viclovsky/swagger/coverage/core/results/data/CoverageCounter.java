package com.github.viclovsky.swagger.coverage.core.results.data;

public class CoverageCounter {
    private long all = 0;
    private long full = 0;
    private long party = 0;
    private long empty = 0;
    private long deprecated = 0;

    public CoverageCounter incrementByState(CoverageState state) {
        switch (state) {
            case FULL:
                return incrementFull();
            case EMPTY:
                return incrementEmpty();
            case PARTY:
                return incrementParty();
        }
        return this;
    }

    public CoverageCounter incrementFull() {
        this.all++;
        this.full++;

        return this;
    }

    public CoverageCounter incrementParty() {
        this.all++;
        this.party++;

        return this;
    }

    public CoverageCounter incrementEmpty() {
        this.all++;
        this.empty++;

        return this;
    }

    public CoverageCounter incrementDeprecated() {
        this.deprecated++;

        return this;
    }

    public long getAll() {
        return all;
    }

    public CoverageCounter setAll(long all) {
        this.all = all;
        return this;
    }

    public long getFull() {
        return full;
    }

    public CoverageCounter setFull(long full) {
        this.full = full;
        return this;
    }

    public long getParty() {
        return party;
    }

    public CoverageCounter setParty(long party) {
        this.party = party;
        return this;
    }

    public long getEmpty() {
        return empty;
    }

    public CoverageCounter setEmpty(long empty) {
        this.empty = empty;
        return this;
    }

    public long getDeprecated() {
        return deprecated;
    }

    public CoverageCounter setDeprecated(long deprecated) {
        this.deprecated = deprecated;
        return this;
    }

    @Override
    public String toString() {
        return "CoverageCounter{" +
                "all=" + all +
                ", full=" + full +
                ", party=" + party +
                ", empty=" + empty +
                ", deprecated=" + deprecated +
                '}';
    }
}
