package com.github.viclovsky.swagger.coverage.core;

import io.swagger.models.Operation;
import io.swagger.models.Swagger;
import io.swagger.models.parameters.Parameter;
import org.apache.log4j.Logger;
import com.github.viclovsky.swagger.coverage.core.filter.SwaggerCoverageFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static com.github.viclovsky.swagger.coverage.SwaggerCoverageConstants.BODY_PARAM_NAME;

abstract class SwaggerCoverageCalculator {

    private final static Logger LOGGER = Logger.getLogger(SwaggerCoverageCalculator.class);

    List<SwaggerCoverageFilter> filters;
    Swagger spec;

    SwaggerCoverageCalculator(List<SwaggerCoverageFilter> filters, Swagger spec) {
        this.filters = filters;
        this.spec = spec;
    }

    SwaggerCoverageCalculator(Swagger spec) {
        this.filters = new ArrayList<>();
        this.spec = spec;
    }

    /**
     * @param swagger output from tests
     */
    abstract SwaggerCoverageCalculator addOutput(Swagger swagger);

    /**
     * @return swagger without test coverage
     */
    abstract Object getResults();

    public boolean isEmptyOperation(Operation operation) {
        return (operation.getParameters().isEmpty() && operation.getResponses().isEmpty());
    }

    public static Predicate<Parameter> equalsParam(Parameter parameter) {
        return p -> (p.getName().equals(parameter.getName()) && p.getIn().equals(parameter.getIn()));
    }

    public static Predicate<Parameter> isBody(Parameter parameter) {
        return p -> (BODY_PARAM_NAME.equalsIgnoreCase(p.getIn())
                && BODY_PARAM_NAME.equalsIgnoreCase(parameter.getIn()));
    }
}
