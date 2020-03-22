package com.github.viclovsky.swagger.coverage.core.rule.parameter;

import com.github.viclovsky.swagger.coverage.core.model.Condition;
import com.github.viclovsky.swagger.coverage.core.predicate.ConditionPredicate;
import com.github.viclovsky.swagger.coverage.core.predicate.DefaultBodyConditionPredicate;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.Parameter;

import java.util.List;

import static java.util.Arrays.asList;

public class DefaultBodyConditionRule extends ParameterRule {

    @Override
    public List<Condition> processParameter(Parameter parameter) {
        if (parameter instanceof BodyParameter) {
            Condition condition = new Condition(
                    "Not empty «body» request",
                    ""
            );

            ConditionPredicate predicate = new DefaultBodyConditionPredicate();
            condition.addPredicate(predicate);

            return asList(condition);
        }

        return null;
    }
}
