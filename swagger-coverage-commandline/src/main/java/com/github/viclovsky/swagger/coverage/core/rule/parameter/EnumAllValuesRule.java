package com.github.viclovsky.swagger.coverage.core.rule.parameter;

import com.github.viclovsky.swagger.coverage.core.generator.SwaggerSpecificationProcessor;
import com.github.viclovsky.swagger.coverage.core.model.Condition;
import com.github.viclovsky.swagger.coverage.core.model.SinglePredicateCondition;
import com.github.viclovsky.swagger.coverage.core.predicate.ConditionPredicate;
import com.github.viclovsky.swagger.coverage.core.predicate.ParameterValueConditionPredicate;
import io.swagger.v3.oas.models.parameters.Parameter;

import java.util.List;

public class EnumAllValuesRule extends ParameterConditionRule {

    @Override
    public Condition processParameter(Parameter parameter) {
        List<String> enumValues = SwaggerSpecificationProcessor.extractEnum(parameter);

        if (enumValues != null && !enumValues.isEmpty()) {
            ConditionPredicate predicate = new ParameterValueConditionPredicate(parameter.getName(), parameter.getIn(), enumValues);
            return new SinglePredicateCondition(
                    String.format("%s «%s» contains all values from enum %s", parameter.getIn(), parameter.getName(), enumValues),
                    "",
                    predicate
            );
        }

        return null;
    }

    @Override
    public String getId() {
        return "enum-all-value";
    }
}
