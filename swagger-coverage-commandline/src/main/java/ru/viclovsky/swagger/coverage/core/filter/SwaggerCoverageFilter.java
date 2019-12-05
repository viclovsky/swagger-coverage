package ru.viclovsky.swagger.coverage.core.filter;

import ru.viclovsky.swagger.coverage.model.OperationCoverage;

public interface SwaggerCoverageFilter {

    /**
     * @param operation
     */
    void apply(OperationCoverage operation);
}
