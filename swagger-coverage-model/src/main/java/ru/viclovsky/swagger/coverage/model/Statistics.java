package ru.viclovsky.swagger.coverage.model;

public class Statistics {
    private int all;
    private int empty;
    private int partial;
    private int full;

    public int getAll() {
        return all;
    }

    public Statistics setAllCount(int allCount) {
        this.all = allCount;
        return this;
    }

    public int getEmpty() {
        return empty;
    }

    public Statistics setEmptyCount(int emptyCount) {
        this.empty = emptyCount;
        return this;
    }

    public int getPartial() {
        return partial;
    }

    public Statistics setPartialCount(int partialCount) {
        this.partial = partialCount;
        return this;
    }

    public int getFull() {
        return full;
    }

    public Statistics setFullCount(int fullCount) {
        this.full = fullCount;
        return this;
    }
}
