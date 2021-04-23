package com.github.viclovsky.swagger.coverage.core.predicate;

import io.swagger.v3.oas.models.Operation;

public abstract class ConditionPredicate {

    public abstract boolean check(Operation operation);

    public abstract boolean postCheck();

    public abstract boolean hasPostCheck();

    public abstract String getReason();
}
