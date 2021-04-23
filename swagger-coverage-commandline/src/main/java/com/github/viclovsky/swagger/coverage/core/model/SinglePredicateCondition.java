package com.github.viclovsky.swagger.coverage.core.model;

import com.github.viclovsky.swagger.coverage.core.predicate.ConditionPredicate;
import io.swagger.v3.oas.models.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SinglePredicateCondition extends Condition {

    private static final Logger LOGGER = LoggerFactory.getLogger(SinglePredicateCondition.class);

    protected ConditionPredicate predicate;

    public SinglePredicateCondition(String name, String description) {
        super(name, description);
    }

    public SinglePredicateCondition(String name, String description, ConditionPredicate predicate) {
        super(name, description);
        this.predicate = predicate;
    }

    @Override
    public void postCheck() {
        this.covered = predicate.postCheck();
    }

    @Override
    public boolean isHasPostCheck() {
        return predicate.hasPostCheck();
    }

    @Override
    public boolean isNeedCheck() {
        return !this.covered || predicate.hasPostCheck();
    }

    @Override
    public boolean check(Operation operation) {
        this.covered = predicate.check(operation);
        return this.covered;
    }

    @Override
    public String getReason() {
        if (predicate.getReason() != null) {
            return predicate.getReason();
        }

        return "";
    }

    @Override
    public String getType() {
        return predicate.getClass().getSimpleName();
    }

}
