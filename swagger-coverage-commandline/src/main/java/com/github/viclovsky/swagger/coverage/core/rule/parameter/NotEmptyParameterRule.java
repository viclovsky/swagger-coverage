package com.github.viclovsky.swagger.coverage.core.rule.parameter;

import com.github.viclovsky.swagger.coverage.core.model.Condition;
import com.github.viclovsky.swagger.coverage.core.model.SinglePredicateCondition;
import com.github.viclovsky.swagger.coverage.core.predicate.ConditionPredicate;
import com.github.viclovsky.swagger.coverage.core.predicate.DefaultParameterConditionPredicate;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.Parameter;

public class NotEmptyParameterRule extends ParameterConditionRule {

    @Override
    public Condition processParameter(Parameter parameter) {
        if (parameter instanceof BodyParameter) {
            return null;
        }

        ConditionPredicate predicate = new DefaultParameterConditionPredicate(false, parameter.getName(), parameter.getIn());
        return new SinglePredicateCondition(
                String.format("%s «%s» is not empty", parameter.getIn(),  parameter.getName()),
                "",
                predicate
        );
    }

    @Override
    public String getId() {
        return "parameter-not-empty";
    }
}
