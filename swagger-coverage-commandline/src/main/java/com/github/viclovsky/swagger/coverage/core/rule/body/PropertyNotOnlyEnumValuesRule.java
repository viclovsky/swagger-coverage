package com.github.viclovsky.swagger.coverage.core.rule.body;

import com.github.viclovsky.swagger.coverage.core.generator.SwaggerSpecificationProcessor;
import com.github.viclovsky.swagger.coverage.core.model.Condition;
import com.github.viclovsky.swagger.coverage.core.model.SinglePredicateCondition;
import com.github.viclovsky.swagger.coverage.core.predicate.PropertyValueNotOnlyConditionPredicate;
import io.swagger.v3.oas.models.media.Schema;

import java.util.List;

public class PropertyNotOnlyEnumValuesRule extends PropertyConditionRule {

    @Override
    protected Condition processProperty(String mediaTypeName, String name, Schema schema) {
        List<String> enums = SwaggerSpecificationProcessor.extractEnum(schema);
        if (schema != null && name != null && mediaTypeName != null
                && enums != null && !enums.isEmpty()) {
            return new SinglePredicateCondition(
                    String.format("«%s» contains all values from enum %s", name, enums),
                    "",
                    new PropertyValueNotOnlyConditionPredicate(mediaTypeName, name, enums)
            );
        }
        return null;
    }

    @Override
    public String getId() {
        return "property-enum-another-value";
    }

}
