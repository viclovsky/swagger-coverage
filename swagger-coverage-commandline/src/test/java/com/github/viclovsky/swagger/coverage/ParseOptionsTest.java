package com.github.viclovsky.swagger.coverage;

import com.github.viclovsky.swagger.coverage.core.generator.Generator;
import org.junit.Test;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static java.nio.file.Paths.get;
import static org.hamcrest.MatcherAssert.assertThat;

public class ParseOptionsTest {
    private static final String V3_OUTPUT_SWAGGER_COVERAGE_DIR = "v3/swagger-coverage-output";
    private static final String CONFIGURATION_FILE = "full_configuration.json";

    @Test
    public void resolveOptionTest() {
        Config config = new Config(CONFIGURATION_FILE, V3_OUTPUT_SWAGGER_COVERAGE_DIR, "v3/petstory_ref_operations.yaml");

        new Generator()
                .setInputPath(config.getOutput())
                .setSpecPath(config.getSpec())
                .setConfigurationPath(config.getPath())
                .run();

        assertThat(get("json-report.json").toFile(), hasJsonPath("$.operations.[\"/pet/{petId} GET\"]"));
    }
}
