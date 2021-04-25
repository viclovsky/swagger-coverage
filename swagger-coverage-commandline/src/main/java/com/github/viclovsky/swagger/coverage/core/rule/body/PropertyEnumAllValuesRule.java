package com.github.viclovsky.swagger.coverage.core.rule.body;

import com.github.viclovsky.swagger.coverage.core.generator.SwaggerSpecificationProcessor;
import com.github.viclovsky.swagger.coverage.core.model.Condition;
import com.github.viclovsky.swagger.coverage.core.model.SinglePredicateCondition;
import com.github.viclovsky.swagger.coverage.core.predicate.PropertyValueConditionPredicate;
import io.swagger.v3.oas.models.media.Schema;

import java.util.List;

public class PropertyEnumAllValuesRule extends PropertyConditionRule {

    @Override
    protected Condition processProperty(String mediaTypeName, Schema schema) {
        List<String> enums = SwaggerSpecificationProcessor.extractEnum(schema);
        if (schema != null && schema.getName() != null && mediaTypeName != null
                && enums != null && !enums.isEmpty()) {
            return new SinglePredicateCondition(
                    String.format("«%s» contains all values from enum %s", schema.getName(), enums),
                    "",
                    new PropertyValueConditionPredicate(mediaTypeName, schema.getName(), enums)
            );
        }
        return null;
    }

    @Override
    public String getId() {
        return "property-enum-all-value";
    }

}
