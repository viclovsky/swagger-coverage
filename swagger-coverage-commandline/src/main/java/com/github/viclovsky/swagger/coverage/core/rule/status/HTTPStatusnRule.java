package com.github.viclovsky.swagger.coverage.core.rule.status;

import com.github.viclovsky.swagger.coverage.core.model.Condition;
import com.github.viclovsky.swagger.coverage.core.model.SinglePredicateCondition;
import com.github.viclovsky.swagger.coverage.core.predicate.ConditionPredicate;
import com.github.viclovsky.swagger.coverage.core.predicate.DefaultStatusConditionPredicate;

public class HTTPStatusnRule extends StatusConditionRule {

    @Override
    public String getId() {
        return "status";
    }

    @Override
    public Condition processStatus(String status) {
        if (skip(status)) {
            return null;
        }

        ConditionPredicate predicate = new DefaultStatusConditionPredicate(status);
        Condition condition = new SinglePredicateCondition(
            "HTTP status " + status,
            "",
            predicate
        );
        return condition;
    }

    protected boolean skip(String status){
        if (this.options == null) {
            return false;
        }
        if (
            this.options.getFilter() != null
            && !this.options.getFilter().isEmpty()
            && !this.options.getFilter().contains(status)
        ) {
            return true;
        }

        if (
            this.options.getIgnore() != null
            && !this.options.getIgnore().isEmpty()
            && this.options.getIgnore().contains(status)
        ) {
            return true;
        }

        return false;
    }
}
