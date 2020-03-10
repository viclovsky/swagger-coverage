package com.github.viclovsky.swagger.coverage.branch.test;

import com.github.viclovsky.swagger.coverage.branch.generator.Generator;
import org.junit.Test;

import java.nio.file.*;

public class SimpleTest {

    @Test
    public void test1(){
        Generator generator = new Generator();
        generator
            .setInputPath(Paths.get("/home/amakovetskiy/work/mts-by-family-backend-test/target/swagger-coverage-output/"))
            .setSpecPath(Paths.get("/home/amakovetskiy/work/mts-by-family-backend-test/swagger.json"))
            .run();
    }

}
