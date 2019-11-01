package ru.viclovsky.swagger.coverage;

import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

public class CompareTest {

    private static final String SWAGGER_FILE_NAME = "petstory.json";
    private static final String PARTIAL_COVERAGE_FILE_NAME = "one_partial_coverage.json";
    private static final String ONE_COVERAGE_FILE_NAME = "one_coverage.json";
    private Swagger swagger;
    private Swagger swagger2;
    private Swagger partialCoverage;
    private Swagger fullCoverage;

    @Before
    public void initSwagger() {
        File file = new File(getClass().getClassLoader().getResource(SWAGGER_FILE_NAME).getFile());
        File coverageFile = new File(getClass().getClassLoader().getResource(PARTIAL_COVERAGE_FILE_NAME).getFile());
        File coverageFile2 = new File(getClass().getClassLoader().getResource(ONE_COVERAGE_FILE_NAME).getFile());
        swagger = new SwaggerParser().read(file.getAbsolutePath());
        swagger2 = new SwaggerParser().read(file.getAbsolutePath());
        partialCoverage =  new SwaggerParser().read(coverageFile.getAbsolutePath());
        fullCoverage =  new SwaggerParser().read(coverageFile2.getAbsolutePath());
    }


    @Test
    public void shouldSeeAllEmpty() {
        Output compare = new Compare(swagger).getOutput();
        assertThat(compare.getEmptyCoverage().keySet(), hasSize(20));
        assertThat(compare.getPartialCoverage().keySet(), hasSize(0));
        assertThat(compare.getFullCoverage().keySet(), hasSize(0));
    }

    @Test
    public void shouldSeeKey() {
        Output compare = new Compare(swagger).getOutput();
        assertThat(compare.getEmptyCoverage().containsKey("GET /pet/{petId}"), equalTo(true));
        assertThat(compare.getEmptyCoverage().containsKey("POST /pet/{petId}"), equalTo(true));
        assertThat(compare.getEmptyCoverage().containsKey("DELETE /pet/{petId}"), equalTo(true));
        assertThat(compare.getEmptyCoverage().containsKey("POST /pet/{petId}/uploadImage"), equalTo(true));
    }


    @Test
    public void shouldSeeAllFull() {
        Output compare = new Compare(swagger).addCoverage(swagger2).getOutput();
        assertThat(compare.getEmptyCoverage().keySet(), hasSize(0));
        assertThat(compare.getPartialCoverage().keySet(), hasSize(0));
        assertThat(compare.getFullCoverage().keySet(), hasSize(20));
    }


    @Test
    @Ignore("java.util.ConcurrentModificationException")
    public void shouldSeeAddAll() {
        Output compare = new Compare(swagger).addCoverage(swagger).getOutput();
        assertThat(compare.getEmptyCoverage().keySet(), hasSize(0));
        assertThat(compare.getPartialCoverage().keySet(), hasSize(0));
        assertThat(compare.getFullCoverage().keySet(), hasSize(20));
    }


    @Test
    public void shouldAddPartial() {
        Output compare = new Compare(swagger).addCoverage(partialCoverage).getOutput();
        assertThat(compare.getEmptyCoverage().keySet(), hasSize(19));
        assertThat(compare.getPartialCoverage().keySet(), hasSize(1));
        assertThat(compare.getFullCoverage().keySet(), hasSize(0));
    }

    @Test
    public void shouldAddFull() {
        Output compare = new Compare(swagger).addCoverage(fullCoverage).getOutput();
        assertThat(compare.getEmptyCoverage().keySet(), hasSize(19));
        assertThat(compare.getPartialCoverage().keySet(), hasSize(0));
        assertThat(compare.getFullCoverage().keySet(), hasSize(1));
    }
}
