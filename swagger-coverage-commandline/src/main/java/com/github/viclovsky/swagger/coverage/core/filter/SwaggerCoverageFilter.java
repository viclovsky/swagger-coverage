package com.github.viclovsky.swagger.coverage.core.filter;

import com.github.viclovsky.swagger.coverage.model.OperationCoverage;

public interface SwaggerCoverageFilter {

    /**
     * @param operation
     */
    void apply(OperationCoverage operation);
}
