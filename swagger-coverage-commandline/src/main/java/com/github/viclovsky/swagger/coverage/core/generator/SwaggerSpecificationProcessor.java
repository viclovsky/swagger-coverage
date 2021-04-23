package com.github.viclovsky.swagger.coverage.core.generator;

import com.github.viclovsky.swagger.coverage.core.model.OperationKey;
import com.github.viclovsky.swagger.coverage.core.model.OperationsHolder;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SwaggerSpecificationProcessor {

    private static final String X_EXAMPLE = "x-example";

    public static OperationsHolder extractOperation(OpenAPI swagger) {
        OperationsHolder operations = new OperationsHolder();

        swagger.getPaths().keySet().forEach(path
                -> swagger.getPaths().get(path).readOperationsMap().forEach((httpMethod, operation)
                -> operations.addOperation(new OperationKey().setPath(path).setHttpMethod(httpMethod), operation)
        ));
        return operations;
    }

    public static String extractValue(Parameter p) {
        if (p.getExtensions() != null && p.getExtensions().containsKey(X_EXAMPLE)) {
            return (String) p.getExtensions().get(X_EXAMPLE);
        }
        if(p.getExample() != null) {
            return p.getExample().toString();
        }
        return p.getName();
    }

    public static String extractValue(Schema schema) {
        if (schema.getExtensions() !=null && schema.getExtensions().containsKey(X_EXAMPLE)) {
            return (String) schema.getExtensions().get(X_EXAMPLE);
        }
        if(schema.getExample() != null) {
            return schema.getExample().toString();
        }

        return schema.getName();
    }

    public static List<String> extractEnum(Parameter p) {
        return extractEnum(p.getSchema());
    }

    public static List<String> extractEnum(Schema schema) {
        List enums = null;
        if (schema != null) {
            enums = schema.getEnum();
            if (enums == null &&
                    schema instanceof ArraySchema &&
                    ((ArraySchema) schema).getItems() != null) {
                enums = ((ArraySchema) schema).getItems().getEnum();
            }
        }
        if (enums != null) {
            return ((Stream<Object>) enums.stream())
                    .map(o -> o.toString())
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }

}
