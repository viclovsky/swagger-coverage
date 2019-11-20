package ru.viclovsky.swagger.coverage.core.filter;

import io.swagger.models.Operation;

public interface SwaggerCoverageFilter {

    /**
     * @param operation
     */
    void apply(Operation operation);
}
