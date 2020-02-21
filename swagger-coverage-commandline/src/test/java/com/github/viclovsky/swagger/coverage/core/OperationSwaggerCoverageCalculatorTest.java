package com.github.viclovsky.swagger.coverage.core;

import com.github.viclovsky.swagger.coverage.model.SwaggerCoverageResults;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

public class OperationSwaggerCoverageCalculatorTest {

    private static final String SWAGGER_FILE_NAME = "petstory.json";
    private static final String PARTIAL_COVERAGE_FILE_NAME = "swagger-coverage-output/one_partial_coverage.json";
    private static final String ONE_COVERAGE_FILE_NAME = "swagger-coverage-output/one_coverage.json";
    private static final String MISSED_COVERAGE_FILE_NAME = "swagger-coverage-output/missed_in_swagger.json";
    private Swagger swagger;
    private Swagger swagger2;
    private Swagger partialCoverage;
    private Swagger fullCoverage;
    private Swagger missedCoverage;

    @Before
    public void initSwagger() {
        File file = new File(getClass().getClassLoader().getResource(SWAGGER_FILE_NAME).getFile());
        File coverageFile = new File(getClass().getClassLoader().getResource(PARTIAL_COVERAGE_FILE_NAME).getFile());
        File coverageFile2 = new File(getClass().getClassLoader().getResource(ONE_COVERAGE_FILE_NAME).getFile());
        File missedCoverageFile = new File(getClass().getClassLoader().getResource(MISSED_COVERAGE_FILE_NAME).getFile());
        swagger = new SwaggerParser().read(file.getAbsolutePath());
        swagger2 = new SwaggerParser().read(file.getAbsolutePath());
        partialCoverage = new SwaggerParser().read(coverageFile.getAbsolutePath());
        fullCoverage = new SwaggerParser().read(coverageFile2.getAbsolutePath());
        missedCoverage = new SwaggerParser().read(missedCoverageFile.getAbsolutePath());
    }


    @Test
    public void shouldSeeAllEmpty() {
        SwaggerCoverageResults results = (SwaggerCoverageResults) new OperationSwaggerCoverageCalculator(swagger).getResults();
        assertThat(results.getEmptyCoverage().keySet(), hasSize(20));
        assertThat(results.getPartialCoverage().keySet(), hasSize(0));
        assertThat(results.getFullCoverage().keySet(), hasSize(0));
    }

    @Test
    public void shouldSeeKey() {
        SwaggerCoverageResults results = (SwaggerCoverageResults) new OperationSwaggerCoverageCalculator(swagger).getResults();
        assertThat(results.getEmptyCoverage().keySet(), hasItems("/pet/{petId} GET", "/pet/{petId} POST", "/pet/{petId} DELETE",
                "/pet/{petId}/uploadImage POST"));
    }


    @Test
    public void shouldSeeAllFull() {
        SwaggerCoverageResults results = (SwaggerCoverageResults) new OperationSwaggerCoverageCalculator(swagger)
                .addOutput(swagger2).getResults();
        assertThat(results.getEmptyCoverage().keySet(), hasSize(0));
        assertThat(results.getPartialCoverage().keySet(), hasSize(0));
        assertThat(results.getFullCoverage().keySet(), hasSize(20));
    }

    @Test
    public void shouldAddPartial() {
        SwaggerCoverageResults results = (SwaggerCoverageResults) new OperationSwaggerCoverageCalculator(swagger)
                .addOutput(partialCoverage).getResults();
        assertThat(results.getEmptyCoverage().keySet(), hasSize(19));
        assertThat(results.getPartialCoverage().keySet(), hasSize(1));
        assertThat(results.getFullCoverage().keySet(), hasSize(0));
    }

    @Test
    public void shouldAddFull() {
        SwaggerCoverageResults results = (SwaggerCoverageResults) new OperationSwaggerCoverageCalculator(swagger)
                .addOutput(fullCoverage).getResults();
        assertThat(results.getEmptyCoverage().keySet(), hasSize(19));
        assertThat(results.getPartialCoverage().keySet(), hasSize(0));
        assertThat(results.getFullCoverage().keySet(), hasSize(1));
    }

    @Test
    public void shouldAddMissed() {
        SwaggerCoverageResults results = (SwaggerCoverageResults) new OperationSwaggerCoverageCalculator(swagger)
                .addOutput(missedCoverage).getResults();
        assertThat(results.getEmptyCoverage().keySet(), hasSize(20));
        assertThat(results.getPartialCoverage().keySet(), hasSize(0));
        assertThat(results.getFullCoverage().keySet(), hasSize(0));
        assertThat(results.getMissedCoverage().keySet(), hasSize(1));
    }
}
