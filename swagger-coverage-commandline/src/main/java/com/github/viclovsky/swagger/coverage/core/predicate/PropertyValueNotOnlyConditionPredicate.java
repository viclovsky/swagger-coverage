package com.github.viclovsky.swagger.coverage.core.predicate;

import com.github.viclovsky.swagger.coverage.core.generator.SwaggerSpecificationProcessor;
import io.swagger.v3.oas.models.media.Schema;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PropertyValueNotOnlyConditionPredicate extends PropertyConditionPredicate {

    private List<String> expectedValue = new ArrayList<>();
    private List<String> currentValue = new ArrayList<>();
    protected String reason;

    public PropertyValueNotOnlyConditionPredicate(String mediaTypeName, String propertyName, List<String> value) {
        super(mediaTypeName, propertyName);
        expectedValue.addAll(value);
    }

    @Override
    public boolean postCheck() {
        boolean covered = currentValue.containsAll(expectedValue);

        if (!covered) {
            expectedValue.removeAll(currentValue);
            reason = "Missed values " + expectedValue.toString();
        }

        return covered;
    }

    @Override
    public boolean hasPostCheck() {
        return true;
    }

    @Override
    public String getReason() {
        return reason;
    }

    @Override
    protected boolean check(Optional<Schema> schema) {
        if (schema.isPresent()) {
            currentValue.add(SwaggerSpecificationProcessor.extractValue(schema.get()));
        }
        return true;
    }

    public List<String> getValue() {
        return expectedValue;
    }

    @Override
    public PropertyValueNotOnlyConditionPredicate setPropertyName(String propertyName) {
        return (PropertyValueNotOnlyConditionPredicate) super.setPropertyName(propertyName);
    }

    @Override
    public PropertyValueNotOnlyConditionPredicate setMediaTypeName(String mediaTypeName) {
        return (PropertyValueNotOnlyConditionPredicate) super.setMediaTypeName(mediaTypeName);
    }

    public PropertyValueNotOnlyConditionPredicate setValue(List<String> value) {
        this.expectedValue = value;
        return this;
    }

}
