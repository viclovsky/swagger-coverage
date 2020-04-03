package com.github.viclovsky.swagger.coverage.core;

import com.github.viclovsky.swagger.coverage.core.generator.Generator;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;

public class SimpleTest {

    private static final String OUTPUT_SWAGGER_COVERAGE_DIR = "swagger-coverage-output";
    private static final String SPEC_FILE = "petstory.json";
    private static final String CONFIGURATION_FILE = "configuration.json";

    private File spec = new File(getClass().getClassLoader().getResource(SPEC_FILE).getFile());
    private File reqDir = new File(getClass().getClassLoader().getResource(OUTPUT_SWAGGER_COVERAGE_DIR).getFile());
    private File configuration = new File(getClass().getClassLoader().getResource(CONFIGURATION_FILE).getFile());

    @Test
    public void simpleTest() {
        Generator generator = new Generator();
        generator.setInputPath(reqDir.toPath())
            .setSpecPath(spec.toPath())
            .run();
    }

    @Test
    public void simpleTestWithConfiguration() {
        Generator generator = new Generator();
        generator.setInputPath(reqDir.toPath())
            .setSpecPath(spec.toPath())
            .setConfigurationPath(configuration.toPath())
            .run();
    }
}
