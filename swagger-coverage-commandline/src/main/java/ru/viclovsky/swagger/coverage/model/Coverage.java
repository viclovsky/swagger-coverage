package ru.viclovsky.swagger.coverage.model;

import io.swagger.models.Operation;

import java.util.Map;

public class Coverage {

    private Coverage() {
    }

    private Map<String, Operation> empty;
    private Map<String, OperationCoverage> partial;
    private Map<String, OperationCoverage> full;

    public Map<String, Operation> getEmpty() {
        return empty;
    }

    public Coverage withEmpty(Map<String, Operation> empty) {
        this.empty = empty;
        return this;
    }

    public Map<String, OperationCoverage> getPartial() {
        return partial;
    }

    public Coverage withPartial(Map<String, OperationCoverage> partial) {
        this.partial = partial;
        return this;
    }

    public Map<String, OperationCoverage> getFull() {
        return full;
    }

    public Coverage withFull(Map<String, OperationCoverage> full) {
        this.full = full;
        return this;
    }

    public static Coverage coverage() {
        return new Coverage();
    }
}
