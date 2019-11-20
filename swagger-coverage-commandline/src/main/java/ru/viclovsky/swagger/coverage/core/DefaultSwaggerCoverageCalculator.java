package ru.viclovsky.swagger.coverage.core;

import io.swagger.models.Operation;
import io.swagger.models.Swagger;
import org.apache.log4j.Logger;

import java.util.*;

final class DefaultSwaggerCoverageCalculator extends SwaggerCoverageCalculator {

    private final static Logger LOGGER = Logger.getLogger(DefaultSwaggerCoverageCalculator.class);

    public DefaultSwaggerCoverageCalculator(List<SwaggerCoverageFilter> filters, Swagger spec) {
        super(filters, spec);
    }

    public DefaultSwaggerCoverageCalculator(Swagger spec) {
        super(spec);
    }

    @Override
    public DefaultSwaggerCoverageCalculator addOutput(Swagger output) {
        output.getPaths().forEach((k, path) -> {
            if (spec.getPaths().containsKey(k)) {
                path.getOperationMap().keySet().forEach(httpMethod -> {
                    if (spec.getPaths().get(k).getOperationMap().containsKey(httpMethod)) {
                        LOGGER.info(String.format("Process %s %s...", httpMethod, k));
                        Operation operationActual = path.getOperationMap().get(httpMethod);
                        Operation operationExpected = spec.getPaths().get(k).getOperationMap().get(httpMethod);
                        processOperation(operationActual, operationExpected);
                    }
                });
            }
        });

        return this;
    }

    @Override
    public Object getResults() {
        return spec;
    }
}
