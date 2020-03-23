package com.github.viclovsky.swagger.coverage.core.rule.status;

import com.github.viclovsky.swagger.coverage.core.model.Condition;
import com.github.viclovsky.swagger.coverage.core.predicate.ConditionPredicate;
import com.github.viclovsky.swagger.coverage.core.predicate.FullStatusConditionPredicate;
import com.github.viclovsky.swagger.coverage.core.rule.ConditionRule;
import io.swagger.models.Operation;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class OnlyDeclaredHTTPStatuses extends ConditionRule {

    @Override
    public List<Condition> createCondition(Operation operation) {
        Condition condition = new Condition(
                "Only declared status",
                ""
        );

        ConditionPredicate predicate = new FullStatusConditionPredicate(operation.getResponses().keySet());
        condition.addPredicate(predicate);

        return singletonList(condition);
    }
}
