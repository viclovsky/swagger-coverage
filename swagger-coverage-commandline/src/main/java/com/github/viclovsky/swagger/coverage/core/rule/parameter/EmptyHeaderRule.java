package com.github.viclovsky.swagger.coverage.core.rule.parameter;

import com.github.viclovsky.swagger.coverage.core.model.Condition;
import com.github.viclovsky.swagger.coverage.core.model.SinglePredicateCondition;
import com.github.viclovsky.swagger.coverage.core.predicate.ConditionPredicate;
import com.github.viclovsky.swagger.coverage.core.predicate.DefaultParameterConditionPredicate;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import io.swagger.v3.oas.models.parameters.Parameter;

public class EmptyHeaderRule extends ParameterConditionRule {

    @Override
    public Condition processParameter(Parameter parameter) {
        if (parameter instanceof HeaderParameter) {
            ConditionPredicate predicate = new DefaultParameterConditionPredicate(true, parameter.getName(), parameter.getIn());
            return new SinglePredicateCondition(
                    String.format("header «%s» is empty", parameter.getName()),
                    "",
                    predicate
            );
        }

        return null;
    }

    @Override
    public String getId() {
        return "empty-required-header";
    }
}
