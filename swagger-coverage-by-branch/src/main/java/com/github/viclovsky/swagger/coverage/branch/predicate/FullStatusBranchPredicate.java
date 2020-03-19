package com.github.viclovsky.swagger.coverage.branch.predicate;

import io.swagger.models.Response;
import io.swagger.models.parameters.Parameter;

import java.util.*;

public class FullStatusBranchPredicate extends BranchPredicate{

    Set<String> expectedStatuses;
    Set<String> currentStatuses = new HashSet<>();
    protected String reason;

    public FullStatusBranchPredicate(Set<String> expectedStatuses) {
        this.expectedStatuses = expectedStatuses;
    }

    @Override
    public boolean check(Map<String, Parameter> params, Map<String, Response> responses) {
        responses.entrySet().stream().forEach(entry -> {
            currentStatuses.add(entry.getKey());
        });
        return true;
    }

    @Override
    public boolean postCheck() {
        boolean covered = expectedStatuses.containsAll(currentStatuses);

        if (!covered){
            currentStatuses.removeAll(expectedStatuses);
            reason = "Undeclared status: " + String.join(",",currentStatuses);
        }

        return covered;
    }

    @Override
    public boolean hasPostCheck() {
        return true;
    }

    @Override
    public String getReason() {
        return reason;
    }

    public Set<String> getExpectedStatuses() {
        return expectedStatuses;
    }

    public FullStatusBranchPredicate setExpectedStatuses(Set<String> expectedStatuses) {
        this.expectedStatuses = expectedStatuses;
        return this;
    }

    public Set<String> getCurrentStatuses() {
        return currentStatuses;
    }

    public FullStatusBranchPredicate setCurrentStatuses(Set<String> currentStatuses) {
        this.currentStatuses = currentStatuses;
        return this;
    }
}
