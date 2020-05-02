package com.github.viclovsky.swagger.coverage.core.generator;


import com.github.viclovsky.swagger.coverage.core.model.OperationKey;
import com.github.viclovsky.swagger.coverage.core.model.OperationsHolder;
import io.swagger.models.Swagger;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.SerializableParameter;
import io.swagger.models.properties.Property;
import io.swagger.models.properties.StringProperty;

import java.util.List;

public class SwaggerSpecificationProcessor {

    private static final String X_EXAMPLE = "x-example";

    public static OperationsHolder extractOperation(Swagger swagger) {
        return extractOperation(swagger, false);
    }

    public static OperationsHolder extractOperation(Swagger swagger, boolean ignoreCase) {
        OperationsHolder operations = new OperationsHolder();

        swagger.getPaths().keySet().forEach(path
                -> swagger.getPaths().get(path).getOperationMap().forEach((httpMethod, operation)
                -> operations.addOperation(new OperationKey().setPath(path).setHttpMethod(httpMethod), operation)
        ));
        return operations;
    }

    public static String extractValue(Parameter p) {
        if (p.getVendorExtensions() == null) {
            return p.getName();
        }

        if (p.getVendorExtensions().containsKey(X_EXAMPLE)) {
            return (String) p.getVendorExtensions().get(X_EXAMPLE);
        }

        return p.getName();
    }

    public static List<String> extractEnum(Parameter p) {
        List<String> enumValues = null;

        if (p instanceof SerializableParameter) {
            SerializableParameter serializableParameter = (SerializableParameter) p;
            if (serializableParameter.getEnum() != null && !serializableParameter.getEnum().isEmpty()) {
                enumValues = serializableParameter.getEnum();
            }

            if (enumValues == null && serializableParameter.getItems() != null) {
                Property items = serializableParameter.getItems();
                if (items instanceof StringProperty) {
                    StringProperty stringProperty = (StringProperty) items;
                    if (stringProperty.getEnum() != null && !stringProperty.getEnum().isEmpty()) {
                        enumValues = stringProperty.getEnum();
                    }
                }
            }
        }

        return enumValues;
    }
}
