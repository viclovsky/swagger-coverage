package com.github.viclovsky.swagger.coverage.core.rule.parameter;


import com.github.viclovsky.swagger.coverage.core.generator.SwaggerSpecificationProcessor;
import com.github.viclovsky.swagger.coverage.core.model.Condition;
import com.github.viclovsky.swagger.coverage.core.model.SinglePredicateCondition;
import com.github.viclovsky.swagger.coverage.core.predicate.ConditionPredicate;
import com.github.viclovsky.swagger.coverage.core.predicate.NotOnlyParameterListValueConditionPredicate;
import io.swagger.models.parameters.Parameter;

import java.util.List;

public class NotOnlyEnumValuesRule extends ParameterConditionRule {


    @Override
    public Condition processParameter(Parameter parameter) {
        List<String> enumValues = SwaggerSpecificationProcessor.extractEnum(parameter);

        if (enumValues != null && !enumValues.isEmpty()) {


            ConditionPredicate predicate = new NotOnlyParameterListValueConditionPredicate(
                    parameter.getName(),parameter.getIn(),enumValues
            );

            Condition condition = new SinglePredicateCondition(
                    parameter.getIn() + " {" + parameter.getName() + "} contains values not only from enum " + enumValues,
                    "",
                    predicate
            );

            return condition;
        }

        return null;
    }

    @Override
    public String getId() {
        return "enum-another-value";
    }
}
