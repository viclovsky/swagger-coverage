package ru.viclovsky.swagger.coverage.model;

import io.swagger.models.Operation;

public class OperationPair {

    private Operation original;
    private Operation modified;

    public static OperationPair operationPair() {
        return new OperationPair();
    }

    public Operation getModified() {
        return modified;
    }

    public OperationPair withModified(Operation modified) {
        this.modified = modified;
        return this;
    }

    public Operation getOriginal() {
        return original;
    }

    public OperationPair withOriginal(Operation original) {
        this.original = original;
        return this;
    }

    private OperationPair() {
    }
}
