package com.github.viclovsky.swagger.coverage.core.rule.parameter;

import com.github.viclovsky.swagger.coverage.core.generator.SwaggerSpecificationProcessor;
import com.github.viclovsky.swagger.coverage.core.model.Condition;
import com.github.viclovsky.swagger.coverage.core.predicate.ConditionPredicate;
import com.github.viclovsky.swagger.coverage.core.predicate.DefaultParameterValueConditionPredicate;
import io.swagger.models.parameters.Parameter;

import java.util.ArrayList;
import java.util.List;

public class DefaultEnumValuesConditionRule extends ParameterRule {

    @Override
    public List<Condition> processParameter(Parameter parameter) {
        List<String> enumValues = SwaggerSpecificationProcessor.extractEnum(parameter);
        List<Condition> conditions = new ArrayList<>();

        if (enumValues != null && !enumValues.isEmpty()) {

            enumValues.forEach(e -> {
                Condition condition = new Condition(
                        String.format("%s «%s» has value «%s» from enum", parameter.getIn(), parameter.getName(), e),
                        ""
                );

                ConditionPredicate predicate = new DefaultParameterValueConditionPredicate(parameter.getName(), parameter.getIn(), e);
                condition.addPredicate(predicate);
                conditions.add(condition);
            });
            return conditions;
        }

        return null;
    }
}
