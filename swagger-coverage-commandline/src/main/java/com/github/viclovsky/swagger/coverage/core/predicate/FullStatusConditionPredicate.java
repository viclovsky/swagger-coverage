package com.github.viclovsky.swagger.coverage.core.predicate;

import io.swagger.models.Response;
import io.swagger.models.parameters.Parameter;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FullStatusConditionPredicate extends ConditionPredicate {

    private Set<String> expectedStatuses;
    private Set<String> currentStatuses = new HashSet<>();
    private String reason;

    public FullStatusConditionPredicate(Set<String> expectedStatuses) {
        this.expectedStatuses = expectedStatuses;
    }

    @Override
    public boolean check(List<Parameter> params, Map<String, Response> responses) {
        responses.forEach((key, value) -> currentStatuses.add(key));
        return true;
    }

    @Override
    public boolean postCheck() {
        boolean covered = expectedStatuses.containsAll(currentStatuses);

        if (!covered){
            currentStatuses.removeAll(expectedStatuses);
            reason = "Undeclared status: " + String.join(",", currentStatuses);
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

    public FullStatusConditionPredicate setExpectedStatuses(Set<String> expectedStatuses) {
        this.expectedStatuses = expectedStatuses;
        return this;
    }

    public Set<String> getCurrentStatuses() {
        return currentStatuses;
    }

    public FullStatusConditionPredicate setCurrentStatuses(Set<String> currentStatuses) {
        this.currentStatuses = currentStatuses;
        return this;
    }
}
