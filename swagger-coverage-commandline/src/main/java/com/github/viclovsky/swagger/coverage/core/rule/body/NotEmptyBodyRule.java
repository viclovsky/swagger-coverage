package com.github.viclovsky.swagger.coverage.core.rule.body;

import com.github.viclovsky.swagger.coverage.core.model.Condition;
import com.github.viclovsky.swagger.coverage.core.model.SinglePredicateCondition;
import com.github.viclovsky.swagger.coverage.core.predicate.DefaultBodyConditionPredicate;
import com.github.viclovsky.swagger.coverage.core.rule.core.ConditionRule;
import io.swagger.v3.oas.models.Operation;

import java.util.ArrayList;
import java.util.List;

public class NotEmptyBodyRule extends ConditionRule {

    @Override
    public List<Condition> createCondition(Operation operation) {
        if (operation.getRequestBody() != null && operation.getRequestBody().getContent() != null) {
            List<Condition> conditions = new ArrayList<>();
            conditions.add(new SinglePredicateCondition(
                    "not empty body request",
                    "",
                    new DefaultBodyConditionPredicate()
            ));
            return conditions;
        } else {
            return null;
        }
    }

    @Override
    public String getId() {
        return "not-empty-body";
    }

}
