package com.github.viclovsky.swagger.coverage.core.rule.parameter;

import com.github.viclovsky.swagger.coverage.core.generator.SwaggerSpecificationProcessor;
import com.github.viclovsky.swagger.coverage.core.model.Condition;
import com.github.viclovsky.swagger.coverage.core.predicate.ConditionPredicate;
import com.github.viclovsky.swagger.coverage.core.predicate.NotOnlyParameterListValueConditionPredicate;
import io.swagger.models.parameters.Parameter;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class NotOnlyEnumValuesConditionRule extends ParameterRule {

    @Override
    public List<Condition> processParameter(Parameter parameter) {
        List<String> enumValues = SwaggerSpecificationProcessor.extractEnum(parameter);

        if (enumValues != null && !enumValues.isEmpty()) {
            Condition condition = new Condition(
                    String.format("%s «%s» contains values not only from enum %s", parameter.getIn(),
                            parameter.getName(), enumValues),
                    ""
            );

            ConditionPredicate predicate = new NotOnlyParameterListValueConditionPredicate(parameter.getName(),
                    parameter.getIn(), enumValues);
            condition.addPredicate(predicate);

            return singletonList(condition);
        }

        return null;
    }
}
