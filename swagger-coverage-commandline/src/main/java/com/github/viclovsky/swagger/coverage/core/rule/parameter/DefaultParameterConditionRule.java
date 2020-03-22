package com.github.viclovsky.swagger.coverage.core.rule.parameter;

import com.github.viclovsky.swagger.coverage.core.model.Condition;
import com.github.viclovsky.swagger.coverage.core.predicate.ConditionPredicate;
import com.github.viclovsky.swagger.coverage.core.predicate.DefaultConditionPredicate;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.Parameter;

import java.util.List;

import static java.util.Arrays.asList;

public class DefaultParameterConditionRule extends ParameterRule {

    @Override
    public List<Condition> processParameter(Parameter parameter) {
        if (parameter instanceof BodyParameter) {
            return null;
        }

        Condition condition = new Condition(
                String.format("Not empty %s «%s»",  parameter.getIn(), parameter.getName()),
                ""
        );

        ConditionPredicate predicate = new DefaultConditionPredicate(false, parameter.getName(), parameter.getIn());
        condition.addPredicate(predicate);

        return asList(condition);
    }

}
