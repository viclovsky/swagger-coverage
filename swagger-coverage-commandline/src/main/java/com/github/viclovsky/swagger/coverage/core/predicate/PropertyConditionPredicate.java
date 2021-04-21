package com.github.viclovsky.swagger.coverage.core.predicate;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.Schema;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class PropertyConditionPredicate extends ConditionPredicate {
    protected String mediaTypeName;
    protected String propertyName;

    public PropertyConditionPredicate(String mediaTypeName, String propertyName) {
        this.mediaTypeName = mediaTypeName;
        this.propertyName = propertyName;
    }

    @Override
    public boolean check(Operation operation) {
        if (operation.getRequestBody() == null ||
                operation.getRequestBody().getContent() == null ||
                operation.getRequestBody().getContent().isEmpty()) {
            return false;
        }
        Optional<Schema> schema = operation.getRequestBody().getContent().entrySet()
                .stream()
                .filter(o -> o.getKey().equals(mediaTypeName))
                .map(o -> o.getValue().getSchema())
                .filter(Objects::nonNull)
                .flatMap(o -> (Stream<Schema>) o.getProperties().values().stream())
                .filter(o -> o.getName().equals(propertyName))
                .findFirst();
        return check(schema);
    }

    public String getPropertyName() {
        return propertyName;
    }

    public String getMediaTypeName() { return mediaTypeName; }

    public PropertyConditionPredicate setPropertyName(String propertyName) {
        this.propertyName = propertyName;
        return this;
    }

    public PropertyConditionPredicate setMediaTypeName(String mediaTypeName) {
        this.mediaTypeName = mediaTypeName;
        return this;
    }

    protected abstract boolean check(Optional<Schema> schema);

}
