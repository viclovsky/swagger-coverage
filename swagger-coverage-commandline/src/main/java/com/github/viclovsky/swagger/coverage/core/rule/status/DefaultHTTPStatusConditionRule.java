package com.github.viclovsky.swagger.coverage.core.rule.status;


import com.github.viclovsky.swagger.coverage.core.model.Condition;
import com.github.viclovsky.swagger.coverage.core.predicate.ConditionPredicate;
import com.github.viclovsky.swagger.coverage.core.predicate.DefaultStatusConditionPredicate;

import java.util.List;

import static java.util.Arrays.asList;

public class DefaultHTTPStatusConditionRule extends StatusConditionRule {

    @Override
    public List<Condition> processStatus(String status) {
        Condition condition = new Condition(
                "HTTP status " + status,
                ""
        );

        ConditionPredicate predicate = new DefaultStatusConditionPredicate(status);
        condition.addPredicate(predicate);
        return asList(condition);
    }
}
