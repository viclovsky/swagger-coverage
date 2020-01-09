package com.github.viclovsky.swagger.coverage;

import java.util.UUID;

import static com.github.viclovsky.swagger.coverage.SwaggerCoverageConstants.COVERAGE_OUTPUT_FILE_SUFFIX;

public final class SwaggerCoverageUtils {

    SwaggerCoverageUtils() {
        throw new IllegalStateException("Do not instance");
    }

    public static String generateCoverageOutputName() {
        return generateCoverageOutputName(UUID.randomUUID().toString());
    }

    public static String generateCoverageOutputName(String uuid) {
        return uuid + COVERAGE_OUTPUT_FILE_SUFFIX;
    }
}

