package com.github.viclovsky.swagger.coverage.core.predicate;

import io.swagger.v3.oas.models.media.Schema;

import java.util.Optional;

public class DefaultPropertyConditionPredicate extends PropertyConditionPredicate {

    protected boolean isEmpty;

    public DefaultPropertyConditionPredicate(String mediaTypeName, String propertyName, boolean isEmpty) {
        super(mediaTypeName, propertyName);
        this.isEmpty = isEmpty;
    }

    @Override
    public boolean postCheck() {
        return false;
    }

    @Override
    public boolean hasPostCheck() {
        return false;
    }

    @Override
    public String getReason() {
        return null;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    @Override
    protected boolean check(Optional<Schema> schema) {
        return (isEmpty() ^ schema.isPresent());
    }

    @Override
    public DefaultPropertyConditionPredicate setPropertyName(String propertyName) {
        return (DefaultPropertyConditionPredicate) super.setPropertyName(propertyName);
    }

    @Override
    public DefaultPropertyConditionPredicate setMediaTypeName(String mediaTypeName) {
        return (DefaultPropertyConditionPredicate) super.setMediaTypeName(mediaTypeName);
    }

    DefaultPropertyConditionPredicate setEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
        return this;
    }

}
