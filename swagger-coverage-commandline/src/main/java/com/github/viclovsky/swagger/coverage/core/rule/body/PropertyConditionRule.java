package com.github.viclovsky.swagger.coverage.core.rule.body;

import com.github.viclovsky.swagger.coverage.core.model.Condition;
import com.github.viclovsky.swagger.coverage.core.rule.core.ConditionRule;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//request body properties are the v3 version of form data params
public abstract class PropertyConditionRule extends ConditionRule {

    public List<Condition> createCondition(Operation operation) {
        if (operation.getRequestBody() != null && operation.getRequestBody().getContent() != null) {
            return operation.getRequestBody().getContent().entrySet().stream()
                    .flatMap(m -> processMediaType(m.getKey(), m.getValue()))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }

    private Stream<Condition> processMediaType(String mediaTypeName, MediaType mediaType) {
        if (mediaType.getSchema() != null && mediaType.getSchema().getProperties() != null) {
            return ((Collection<Schema>) mediaType.getSchema().getProperties().values())
                    .stream()
                    .map(s -> processProperty(mediaTypeName, s))
                    .filter(Objects::nonNull);
        } else {
            return null;
        }
    }

    protected abstract Condition processProperty(String mediaTypeName, Schema schema);

}
