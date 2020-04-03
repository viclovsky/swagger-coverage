package com.github.viclovsky.swagger.coverage.core.results.data;

public class ConditionCounter {

    protected long all = 0;
    protected long covered = 0;

    public ConditionCounter updateAll(long count){
        this.all = this.all + count;
        return this;
    }

    public ConditionCounter updateCovered(long count){
        this.covered= this.covered + count;
        return this;
    }

    public long getAll() {
        return all;
    }

    public ConditionCounter setAll(long all) {
        this.all = all;
        return this;
    }

    public long getCovered() {
        return covered;
    }

    public ConditionCounter setCovered(long covered) {
        this.covered = covered;
        return this;
    }

    @Override
    public String toString() {
        return "ConditionCounter{" +
                "all=" + all +
                ", covered=" + covered +
                '}';
    }
}
