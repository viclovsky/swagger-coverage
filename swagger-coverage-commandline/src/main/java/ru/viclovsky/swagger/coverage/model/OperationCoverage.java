package ru.viclovsky.swagger.coverage.model;

import io.swagger.models.Operation;
import io.swagger.models.parameters.Parameter;

import java.util.Set;

public class OperationCoverage {

    private Operation original;
    private Operation modified;

    private Set<Parameter> ignoredParams;
    private Set<String> ignoredStatusCodes;

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

    public Set<Parameter> getIgnoredParams() {
        return ignoredParams;
    }

    public OperationCoverage withIgnoredParams(Set<Parameter> ignoredParams) {
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
