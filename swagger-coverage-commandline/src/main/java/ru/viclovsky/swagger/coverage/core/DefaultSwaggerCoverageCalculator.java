package ru.viclovsky.swagger.coverage.core;

import io.swagger.models.Operation;
import io.swagger.models.Swagger;
import org.apache.log4j.Logger;
import ru.viclovsky.swagger.coverage.core.filter.SwaggerCoverageFilter;

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
                        LOGGER.debug(String.format("Process %s %s...", httpMethod, k));
                        Operation operationActual = path.getOperationMap().get(httpMethod);
                        Operation operationExpected = spec.getPaths().get(k).getOperationMap().get(httpMethod);
                        processOperation(operationActual, operationExpected);
                    }
                });
            }
        });

        return this;
    }

    public Operation processOperation(Operation current, Operation expected) {
        if (!filters.isEmpty()) {
            filters.forEach(filter -> filter.apply(expected));
        }

        if (Objects.nonNull(current.getResponses())) {
            current.getResponses().forEach((status, resp) -> {
                LOGGER.debug(String.format("Remove status code: [%s]", status));
                expected.getResponses().remove(status);
            });
        }

        if (Objects.nonNull(current.getParameters())) {
            current.getParameters().forEach(parameter ->
                    expected.getParameters().stream()
                            .filter(equalsParam(parameter).or(isBody(parameter))).findFirst()
                            .ifPresent(presentParam -> {
                                LOGGER.debug(String.format("Remove %s: [%s]", presentParam.getIn(),
                                        presentParam.getName()));
                                expected.getParameters().remove(presentParam);
                            }));
        }
        return expected;
    }

    @Override
    public Object getResults() {
        return spec;
    }
}
