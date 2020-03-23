package com.github.viclovsky.swagger.coverage.core.rule.parameter;

import com.github.viclovsky.swagger.coverage.core.model.Condition;
import com.github.viclovsky.swagger.coverage.core.predicate.ConditionPredicate;
import com.github.viclovsky.swagger.coverage.core.predicate.DefaultParameterConditionPredicate;
import io.swagger.models.parameters.HeaderParameter;
import io.swagger.models.parameters.Parameter;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class EmptyHeaderConditionRule extends ParameterRule {

    @Override
    public List<Condition> processParameter(Parameter parameter) {
        if (parameter instanceof HeaderParameter) {
            Condition condition = new Condition(String.format("Empty header «%s»", parameter.getName()),
                    ""
            );

            ConditionPredicate predicate = new DefaultParameterConditionPredicate(true, parameter.getName(), parameter.getIn());
            condition.addPredicate(predicate);

            return singletonList(condition);
        }

        return null;
    }
}
