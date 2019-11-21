package ru.viclovsky.swagger.coverage.core;

import io.swagger.models.ParamType;
import org.junit.Test;
import ru.viclovsky.swagger.coverage.config.Configuration;
import ru.viclovsky.swagger.coverage.core.filter.IgnoreParamsFilter;
import ru.viclovsky.swagger.coverage.core.filter.StatusOkFilter;
import ru.viclovsky.swagger.coverage.core.filter.SwaggerCoverageFilter;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class SwaggerCoverageExecTest {

    private static final String OUTPUT_SWAGGER_COVERAGE_DIR = "swagger-coverage-realty-v1";
    private static final String SPEC_FILE = "realty3-api-swagger-v1.json";

    private File spec = new File(getClass().getClassLoader().getResource(SPEC_FILE).getFile());
    private File reqDir = new File(getClass().getClassLoader().getResource(OUTPUT_SWAGGER_COVERAGE_DIR).getFile());

    @Test
    public void shouldExecute() {
        SwaggerCoverageExec.swaggerCoverage(Configuration.conf()
                .setOutputPath(reqDir.toPath()).setSpecPath(spec.toPath())).execute();
    }

    @Test
    public void shouldExecuteSwaggerResults() {
        SwaggerCoverageExec.swaggerCoverage(Configuration.conf()
                .setSwaggerResults(true)
                .setOutputPath(reqDir.toPath()).setSpecPath(spec.toPath())).execute();
    }

    @Test
    public void shouldExecuteFilters() {
        List<SwaggerCoverageFilter> filters = Arrays.asList(
                new StatusOkFilter(), new IgnoreParamsFilter(
                        Arrays.asList("X-Uid", "Authorization"), ParamType.HEADER));
        SwaggerCoverageExec.swaggerCoverage(Configuration.conf()
                .setOutputPath(reqDir.toPath()).setSpecPath(spec.toPath()))
                .setFilters(filters).execute();
    }
}
