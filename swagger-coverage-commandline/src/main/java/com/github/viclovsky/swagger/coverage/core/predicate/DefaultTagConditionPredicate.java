package com.github.viclovsky.swagger.coverage.core.predicate;

import io.swagger.v3.oas.models.Operation;

import java.util.List;

public class DefaultTagConditionPredicate extends ConditionPredicate {

    private String tag;

    public DefaultTagConditionPredicate(String tag) {
        this.tag = tag;
    }

    @Override
    public boolean check(Operation operation) {
        List<String> tags = operation.getTags();
        return tag == null || (tags != null && tags.contains(tag));
    }

    @Override
    public boolean postCheck() {
        return false;
    }

    @Override
    public boolean hasPostCheck() {
        return false;
    }

    @Override
    public String getReason() {
        return null;
    }

}
