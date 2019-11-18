package ru.viclovsky.swagger.coverage;

public final class SwaggerCoverageConstants {

    public static final String BODY_PARAM_NAME = "body";

    public static final String OUTPUT_DIRECTORY = "swagger-coverage-output";
    public static final String COVERAGE_RESULT_FILE_SUFFIX = "-coverage.json";

    private SwaggerCoverageConstants() {
        throw new IllegalStateException("Do not instance");
    }

}
