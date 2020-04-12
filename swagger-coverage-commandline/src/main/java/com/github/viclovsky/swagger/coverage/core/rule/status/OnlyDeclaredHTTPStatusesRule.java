package com.github.viclovsky.swagger.coverage.core.rule.status;

import com.github.viclovsky.swagger.coverage.core.model.Condition;
import com.github.viclovsky.swagger.coverage.core.model.SinglePredicateCondition;
import com.github.viclovsky.swagger.coverage.core.predicate.ConditionPredicate;
import com.github.viclovsky.swagger.coverage.core.predicate.FullStatusConditionPredicate;
import com.github.viclovsky.swagger.coverage.core.rule.core.ConditionRule;
import io.swagger.models.Operation;

import java.util.Arrays;
import java.util.List;

public class OnlyDeclaredHTTPStatusesRule extends ConditionRule {
    @Override
    public String getId() {
        return "only-declared-status";
    }

    @Override
    public List<Condition> createCondition(Operation operation) {
        ConditionPredicate predicate = new FullStatusConditionPredicate(operation.getResponses().keySet());
        Condition condition = new SinglePredicateCondition(
                "Only declared status",
                "",
                predicate
        );

        return Arrays.asList( condition);
    }
}
