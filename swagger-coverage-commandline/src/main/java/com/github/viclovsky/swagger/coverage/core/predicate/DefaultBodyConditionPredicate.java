package com.github.viclovsky.swagger.coverage.core.predicate;

import io.swagger.v3.oas.models.Operation;

public class DefaultBodyConditionPredicate extends ConditionPredicate {

    @Override
    public boolean check(Operation operation) {
        if(operation.getRequestBody() != null && operation.getRequestBody().getContent() != null) {
            return operation.getRequestBody().getContent().values().size() > 0;
        } else {
            return false;
        }
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
