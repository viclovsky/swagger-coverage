package com.github.viclovsky.swagger.coverage;

import com.github.viclovsky.swagger.coverage.core.generator.Generator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class SimpleTest {

    private static final String OUTPUT_SWAGGER_COVERAGE_DIR = "swagger-coverage-output";
    private static final String CONFIGURATION_FILE = "configuration.json";
    private final Config config;

    public SimpleTest(Config config) {
        this.config = config;
    }

    @Parameterized.Parameters()
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][]{
                {new Config(CONFIGURATION_FILE, OUTPUT_SWAGGER_COVERAGE_DIR, "petstory.json")},
                {new Config(CONFIGURATION_FILE, OUTPUT_SWAGGER_COVERAGE_DIR, "petstory_no_tags.json")}
        });
    }

    @Test
    public void simpleTest() {
        new Generator()
                .setInputPath(config.getOutput())
                .setSpecPath(config.getSpec())
                .run();
    }

    @Test
    public void simpleTestWithConfiguration() {
        new Generator()
                .setInputPath(config.getOutput())
                .setSpecPath(config.getSpec())
                .setConfigurationPath(config.getPath())
                .run();
    }
}
