package com.github.viclovsky.swagger.coverage;

import java.util.UUID;

import static com.github.viclovsky.swagger.coverage.SwaggerCoverageConstants.COVERAGE_JSON_OUTPUT_FILE_SUFFIX;
import static com.github.viclovsky.swagger.coverage.SwaggerCoverageConstants.COVERAGE_YAML_OUTPUT_FILE_SUFFIX;

public final class SwaggerCoverageUtils {

    SwaggerCoverageUtils() {
        throw new IllegalStateException("Do not instance");
    }

    public static String generateJsonCoverageOutputName() {
        return generateCoverageOutputName(UUID.randomUUID().toString(), COVERAGE_JSON_OUTPUT_FILE_SUFFIX);
    }

    public static String generateYamlCoverageOutputName() {
        return generateCoverageOutputName(UUID.randomUUID().toString(), COVERAGE_YAML_OUTPUT_FILE_SUFFIX);
    }

    private static String generateCoverageOutputName(String uuid, String suffix) {
        return uuid + suffix;
    }
}

