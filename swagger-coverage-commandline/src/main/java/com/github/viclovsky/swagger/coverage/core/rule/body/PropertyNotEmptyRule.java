package com.github.viclovsky.swagger.coverage.core.rule.body;

import com.github.viclovsky.swagger.coverage.core.model.Condition;
import com.github.viclovsky.swagger.coverage.core.model.SinglePredicateCondition;
import com.github.viclovsky.swagger.coverage.core.predicate.DefaultPropertyConditionPredicate;
import io.swagger.v3.oas.models.media.Schema;

public class PropertyNotEmptyRule extends PropertyConditionRule {

    @Override
    protected Condition processProperty(String mediaTypeName, Schema schema) {
        if (schema != null && mediaTypeName != null) {
            return new SinglePredicateCondition(
                    String.format("«%s» is not empty", schema.getName()),
                    "",
                    new DefaultPropertyConditionPredicate(mediaTypeName, schema.getName(), false)
            );
        }
        return null;
    }

    @Override
    public String getId() {
        return "property-not-empty";
    }

}
