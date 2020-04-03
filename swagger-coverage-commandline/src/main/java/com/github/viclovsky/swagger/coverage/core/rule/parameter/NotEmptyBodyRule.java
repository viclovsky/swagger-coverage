package com.github.viclovsky.swagger.coverage.core.rule.parameter;

import com.github.viclovsky.swagger.coverage.core.model.Condition;
import com.github.viclovsky.swagger.coverage.core.model.SinglePredicateCondition;
import com.github.viclovsky.swagger.coverage.core.predicate.ConditionPredicate;
import com.github.viclovsky.swagger.coverage.core.predicate.DefaultBodyConditionPredicate;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.Parameter;

public class NotEmptyBodyRule extends ParameterConditionRule {
    @Override
    public Condition processParameter(Parameter parameter) {
        if (parameter instanceof BodyParameter) {


            ConditionPredicate predicate = new DefaultBodyConditionPredicate();
            Condition condition = new SinglePredicateCondition(
                    "Not empty body request",
                    "",
                    predicate
            );

            return condition;
        }

        return null;
    }

    @Override
    public String getId() {
        return "not-empty-body";
    }
}
