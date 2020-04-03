package com.github.viclovsky.swagger.coverage.core.rule.parameter;

import com.github.viclovsky.swagger.coverage.core.model.Condition;
import com.github.viclovsky.swagger.coverage.core.model.SinglePredicateCondition;
import com.github.viclovsky.swagger.coverage.core.predicate.ConditionPredicate;
import com.github.viclovsky.swagger.coverage.core.predicate.DefaultParameterConditionPredicate;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.Parameter;

public class DefaultParameterRule extends ParameterConditionRule {
    @Override
    public Condition processParameter(Parameter parameter) {
        if (parameter instanceof BodyParameter) {
            return null;
        }

        ConditionPredicate predicate = new DefaultParameterConditionPredicate(false,parameter.getName(),parameter.getIn());
        Condition branch = new SinglePredicateCondition(
                parameter.getIn() + " {" + parameter.getName() + "} is not empty",
                "",
                predicate
        );

        return branch;
    }

    @Override
    public String getId() {
        return "parameter-not-empty";
    }
}
