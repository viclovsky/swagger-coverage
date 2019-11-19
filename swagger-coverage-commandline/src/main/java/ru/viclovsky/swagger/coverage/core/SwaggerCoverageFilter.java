package ru.viclovsky.swagger.coverage.core;

import io.swagger.models.Operation;

public interface SwaggerCoverageFilter {

    /**
     * @param operation
     */
    void apply(Operation operation);

}
