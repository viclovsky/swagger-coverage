package com.github.viclovsky.swagger.coverage.branch.test;

import com.github.viclovsky.swagger.coverage.branch.generator.Generator;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;

public class SimpleTest {

    private static final String OUTPUT_SWAGGER_COVERAGE_DIR = "swagger-coverage-output";
    private static final String SPEC_FILE = "petstory.json";

    private File spec = new File(getClass().getClassLoader().getResource(SPEC_FILE).getFile());
    private File reqDir = new File(getClass().getClassLoader().getResource(OUTPUT_SWAGGER_COVERAGE_DIR).getFile());

    @Test
    public void simpleTest() {
        Generator generator = new Generator();
        generator.setInputPath(Paths.get(String.valueOf(reqDir.toPath())))
            .setSpecPath(Paths.get(String.valueOf(spec.toPath())))
            .run();
    }

}
