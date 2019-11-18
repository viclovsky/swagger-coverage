package ru.viclovsky.swagger.coverage;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static ru.viclovsky.swagger.coverage.SwaggerCoverageConstants.COVERAGE_OUTPUT_FILE_SUFFIX;
import static ru.viclovsky.swagger.coverage.SwaggerCoverageConstants.COVERAGE_RESULTS_FILE_SUFFIX;

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

    public static String generateCoverageResultsName() {
        return ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME) + COVERAGE_RESULTS_FILE_SUFFIX;
    }
}

