package ru.viclovsky.swagger.coverage.model;

import io.swagger.models.Operation;
import io.swagger.models.parameters.Parameter;

import java.util.HashSet;
import java.util.Set;

public class OperationCoverage {

    private Operation original;
    private Operation modified;

    private Set<Parameter> ignoredParams = new HashSet<>();
    private Set<String> ignoredStatusCodes = new HashSet<>();

    private OperationCoverage() {
    }

    public static OperationCoverage operationCoverage() {
        return new OperationCoverage();
    }

    public Operation getModified() {
        return modified;
    }

    public OperationCoverage setModified(Operation modified) {
        this.modified = modified;
        return this;
    }

    public Operation getOriginal() {
        return original;
    }

    public OperationCoverage setOriginal(Operation original) {
        this.original = original;
        return this;
    }

    public Set<Parameter> getIgnoredParams() {
        return ignoredParams;
    }

    public OperationCoverage setIgnoredParams(Set<Parameter> ignoredParams) {
        this.ignoredParams = ignoredParams;
        return this;
    }

    public Set<String> getIgnoredStatusCodes() {
        return ignoredStatusCodes;
    }

    public OperationCoverage setIgnoredStatusCodes(Set<String> ignoredStatusCodes) {
        this.ignoredStatusCodes = ignoredStatusCodes;
        return this;
    }
}
