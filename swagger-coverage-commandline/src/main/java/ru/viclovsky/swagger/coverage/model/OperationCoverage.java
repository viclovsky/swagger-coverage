package ru.viclovsky.swagger.coverage.model;

import io.swagger.models.Operation;

import java.util.HashSet;
import java.util.Set;

public class OperationCoverage {

    private Operation original;
    private Operation modified;

    private Set<String> ignoredParams = new HashSet<>();
    private Set<String> ignoredStatusCodes = new HashSet<>();

    private OperationCoverage() {
    }

    public static OperationCoverage operationCoverage() {
        return new OperationCoverage();
    }

    public Operation getModified() {
        return modified;
    }

    public OperationCoverage withModified(Operation modified) {
        this.modified = modified;
        return this;
    }

    public Operation getOriginal() {
        return original;
    }

    public OperationCoverage withOriginal(Operation original) {
        this.original = original;
        return this;
    }

    public Set<String> getIgnoredParams() {
        return ignoredParams;
    }

    public OperationCoverage withIgnoredParams(Set<String> ignoredParams) {
        this.ignoredParams = ignoredParams;
        return this;
    }

    public Set<String> getIgnoredStatusCodes() {
        return ignoredStatusCodes;
    }

    public OperationCoverage withIgnoredStatusCodes(Set<String> ignoredStatusCodes) {
        this.ignoredStatusCodes = ignoredStatusCodes;
        return this;
    }
}
