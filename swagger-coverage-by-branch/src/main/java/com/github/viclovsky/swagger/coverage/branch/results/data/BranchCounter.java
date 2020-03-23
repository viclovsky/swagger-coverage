package com.github.viclovsky.swagger.coverage.branch.results.data;

public class BranchCounter {

    protected long all = 0;
    protected long covered = 0;

    public BranchCounter updateAll(long count){
        this.all = this.all + count;
        return this;
    }

    public BranchCounter updateCovered(long count){
        this.covered= this.covered + count;
        return this;
    }

    public long getAll() {
        return all;
    }

    public BranchCounter setAll(long all) {
        this.all = all;
        return this;
    }

    public long getCovered() {
        return covered;
    }

    public BranchCounter setCovered(long covered) {
        this.covered = covered;
        return this;
    }

    @Override
    public String toString() {
        return "BranchCounter{" +
                "all=" + all +
                ", covered=" + covered +
                '}';
    }
}
