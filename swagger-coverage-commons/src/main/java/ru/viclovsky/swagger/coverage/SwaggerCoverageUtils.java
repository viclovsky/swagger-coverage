package ru.viclovsky.swagger.coverage;

import java.util.UUID;

public final class SwaggerCoverageUtils {

    SwaggerCoverageUtils() {
        throw new IllegalStateException("Do not instance");
    }

    public static String generateCoverageResultName() {
        return generateCoverageResultName(UUID.randomUUID().toString());
    }

    public static String generateCoverageResultName(String uuid) {
        return uuid + SwaggerCoverageConstants.COVERAGE_RESULT_FILE_SUFFIX;
    }
}

