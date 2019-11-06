package ru.viclovsky.swagger.coverage.model;

public class Total {
    private int empty;
    private int partial;
    private int full;

    public int getEmpty() {
        return empty;
    }

    public void setEmpty(int empty) {
        this.empty = empty;
    }

    public int getPartial() {
        return partial;
    }

    public void setPartial(int partial) {
        this.partial = partial;
    }

    public int getFull() {
        return full;
    }

    public void setFull(int full) {
        this.full = full;
    }
}
