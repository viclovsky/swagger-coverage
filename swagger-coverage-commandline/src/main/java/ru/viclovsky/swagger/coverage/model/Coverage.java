package ru.viclovsky.swagger.coverage.model;

import io.swagger.models.Operation;

import java.util.Map;

public class Coverage {

    private Coverage() {
    }

    private Map<String, Operation> empty;
    private Map<String, OperationPair> partial;
    private Map<String, OperationPair> full;

    public Map<String, Operation> getEmpty() {
        return empty;
    }

    public Coverage withEmpty(Map<String, Operation> empty) {
        this.empty = empty;
        return this;
    }

    public Map<String, OperationPair> getPartial() {
        return partial;
    }

    public Coverage withPartial(Map<String, OperationPair> partial) {
        this.partial = partial;
        return this;
    }

    public Map<String, OperationPair> getFull() {
        return full;
    }

    public Coverage withFull(Map<String, OperationPair> full) {
        this.full = full;
        return this;
    }

    public static Coverage coverage() {
        return new Coverage();
    }
}
