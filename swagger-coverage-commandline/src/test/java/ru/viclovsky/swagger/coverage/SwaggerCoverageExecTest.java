package ru.viclovsky.swagger.coverage;

import org.junit.Test;
import ru.viclovsky.swagger.coverage.config.Config;
import ru.viclovsky.swagger.coverage.core.SwaggerCoverageExec;

import java.io.File;

public class SwaggerCoverageExecTest {

    private static final String OUTPUT_SWAGGER_COVERAGE_DIR = "swagger-coverage-output";
    private static final String SPEC_FILE = "example_spec.json";

    private File spec = new File(getClass().getClassLoader().getResource(SPEC_FILE).getFile());
    private File reqDir = new File(getClass().getClassLoader().getResource(OUTPUT_SWAGGER_COVERAGE_DIR).getFile());

    @Test
    public void shouldExecute() {
        SwaggerCoverageExec.swaggerCoverage(Config.conf()
                .withInputPath(reqDir.toPath()).withSpecPath(spec.toPath())
                .withIgnoreHeaders(true).withIgnoreStatusCodes(true)).execute();
    }
}
