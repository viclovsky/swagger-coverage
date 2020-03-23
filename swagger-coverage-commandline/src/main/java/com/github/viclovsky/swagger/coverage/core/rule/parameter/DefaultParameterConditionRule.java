package com.github.viclovsky.swagger.coverage.core.rule.parameter;

import com.github.viclovsky.swagger.coverage.core.model.Condition;
import com.github.viclovsky.swagger.coverage.core.predicate.ConditionPredicate;
import com.github.viclovsky.swagger.coverage.core.predicate.DefaultParameterConditionPredicate;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.Parameter;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

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

        ConditionPredicate predicate = new DefaultParameterConditionPredicate(false, parameter.getName(), parameter.getIn());
        condition.addPredicate(predicate);

        return singletonList(condition);
    }

}
