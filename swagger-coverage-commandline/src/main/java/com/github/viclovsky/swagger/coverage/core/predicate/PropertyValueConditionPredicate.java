package com.github.viclovsky.swagger.coverage.core.predicate;

import com.github.viclovsky.swagger.coverage.core.generator.SwaggerSpecificationProcessor;
import io.swagger.v3.oas.models.media.Schema;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PropertyValueConditionPredicate extends PropertyConditionPredicate {

    private List<String> expectedValue = new ArrayList<>();
    private List<String> currentValue = new ArrayList<>();
    protected String reason;

    public PropertyValueConditionPredicate(String mediaTypeName, String propertyName, List<String> value) {
        super(mediaTypeName, propertyName);
        expectedValue.addAll(value);
    }

    @Override
    public boolean postCheck() {
        reason = "Checked values: " + currentValue.toString();
        currentValue.removeAll(expectedValue);
        return !currentValue.isEmpty();
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
    public PropertyValueConditionPredicate setPropertyName(String propertyName) {
        return (PropertyValueConditionPredicate) super.setPropertyName(propertyName);
    }

    @Override
    public PropertyValueConditionPredicate setMediaTypeName(String mediaTypeName) {
        return (PropertyValueConditionPredicate) super.setMediaTypeName(mediaTypeName);
    }

    public PropertyValueConditionPredicate setValue(List<String> value) {
        this.expectedValue = value;
        return this;
    }

}
