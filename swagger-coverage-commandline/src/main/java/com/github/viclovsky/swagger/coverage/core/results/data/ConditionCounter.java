package com.github.viclovsky.swagger.coverage.core.results.data;

public class ConditionCounter {

    private long all = 0;
    private long covered = 0;
    private long deprecated = 0;
    private long deprecatedAndEmpty = 0;

    public ConditionCounter updateAll(long count) {
        this.all = this.all + count;
        return this;
    }

    public ConditionCounter updateCovered(long count) {
        this.covered = this.covered + count;
        return this;
    }

    public ConditionCounter incrementDeprecated() {
        this.deprecated = this.deprecated + 1;
        return this;
    }

    public ConditionCounter incrementDeprecatedAndEmpty() {
        this.deprecatedAndEmpty = this.deprecatedAndEmpty + 1;
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

    public long getDeprecated() {
        return deprecated;
    }

    public void setDeprecated(long deprecated) {
        this.deprecated = deprecated;
    }

    public long getDeprecatedAndEmpty() {
        return deprecatedAndEmpty;
    }

    public void setDeprecatedAndEmpty(long deprecatedAndEmpty) {
        this.deprecatedAndEmpty = deprecatedAndEmpty;
    }

    @Override
    public String toString() {
        return "ConditionCounter{" +
                "all=" + all +
                ", covered=" + covered +
                '}';
    }
}
