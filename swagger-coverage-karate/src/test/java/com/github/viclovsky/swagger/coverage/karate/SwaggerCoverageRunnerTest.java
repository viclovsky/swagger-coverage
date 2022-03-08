package com.github.viclovsky.swagger.coverage.karate;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.github.viclovsky.swagger.coverage.SwaggerCoverageConstants;
import com.intuit.karate.FileUtils;
import com.intuit.karate.Results;

import org.apache.http.HttpStatus;
import org.hamcrest.io.FileMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static com.github.viclovsky.swagger.coverage.SwaggerCoverageConstants.COVERAGE_HTML_REPORT_NAME;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.hamcrest.Matchers.iterableWithSize;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public class SwaggerCoverageRunnerTest {

    private static final String BODY_STRING = "{ name: MockPet }";

    @Rule
    public WireMockRule mock = new WireMockRule(options().dynamicPort().withRootDirectory(getDirFromResources("/wiremock").toString()), false);

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUp(){
        configureFor(mock.port());
        stubFor(get(urlMatching("/pet/.*"))
                .willReturn(aResponse().withStatus(HttpStatus.SC_OK)
                        .withBody(BODY_STRING)));

        deleteOutputDirs(getDirFromResources("/api-test-coverage-v2"));
        deleteOutputDirs(getDirFromResources("/api-test-coverage-v3"));
    }

    @Test
    public void shouldGetSpecFromUrlV2() throws IOException{
        File tempCoverageDir = folder.newFolder();
        Results results = SwaggerCoverageRunner.path("classpath:petv2.feature")
                .backupReportDir(false)
                .coverageDir(tempCoverageDir.toString())
                .swaggerSpec(URI.create(mock.url("/swagger.json")))
                .swagger()
                .systemProperty("baseUrl", mock.baseUrl())
                .parallel(1);

        assertEquals(results.getErrorMessages(), 0, results.getFailCount());
        assertThat(Paths.get(COVERAGE_HTML_REPORT_NAME).toFile(), FileMatchers.anExistingFile());
    }

    @Test
    public void shouldGetSpecFromUrlV3() throws IOException{
        File tempCoverageDir = folder.newFolder();
        Results results = SwaggerCoverageRunner.path("classpath:petv3.feature")
                .backupReportDir(false)
                .coverageDir(tempCoverageDir.toString())
                .swaggerSpec(URI.create(mock.url("/openapi.yaml")))
                .oas3()
                .systemProperty("baseUrl", mock.baseUrl())
                .parallel(1);

        assertEquals(results.getErrorMessages(), 0, results.getFailCount());
        assertThat(Paths.get(COVERAGE_HTML_REPORT_NAME).toFile(), FileMatchers.anExistingFile());
    }

    @Test
    public void shouldGetFilesFromSpecifiedFilesV2(){
        Path coverageDir = getDirFromResources("/api-test-coverage-v2");
        Results results = SwaggerCoverageRunner.path("classpath:petv2.feature")
                .backupReportDir(false)
                .coverageDir(coverageDir.toString())
                .swaggerSpec(coverageDir.resolve("swagger-specification.json").toUri())
                .swaggerCoverageConfig(coverageDir.resolve("swagger-coverage-config.json").toString())
                .swagger()
                .systemProperty("baseUrl", mock.baseUrl())
                .parallel(1);

        assertEquals(results.getErrorMessages(), 0, results.getFailCount());
        assertThat(Paths.get(COVERAGE_HTML_REPORT_NAME).toFile(), FileMatchers.anExistingFile());
    }

    @Test
    public void shouldGetFilesFromSpecifiedFilesV3(){
        Path coverageDir = getDirFromResources("/api-test-coverage-v3");
        Results results = SwaggerCoverageRunner.path("classpath:petv3.feature")
                .backupReportDir(false)
                .coverageDir(coverageDir.toString())
                .swaggerSpec(coverageDir.resolve("swagger-specification.yaml").toUri())
                .swaggerCoverageConfig(coverageDir.resolve("swagger-coverage-config.json").toString())
                .oas3()
                .systemProperty("baseUrl", mock.baseUrl())
                .parallel(1);

        assertEquals(results.getErrorMessages(), 0, results.getFailCount());
        assertThat(Paths.get(COVERAGE_HTML_REPORT_NAME).toFile(), FileMatchers.anExistingFile());
    }


    @Test
    public void shouldGetFilesFromSpecifiedDirV2(){
        Path coverageDir = getDirFromResources("/api-test-coverage-v2");
        Results results = SwaggerCoverageRunner.path("classpath:petv2.feature")
                .backupReportDir(false)
                .coverageDir(coverageDir.toString())
                .swagger()
                .systemProperty("baseUrl", mock.baseUrl())
                .parallel(1);

        assertEquals(results.getErrorMessages(), 0, results.getFailCount());
        assertThat(Paths.get(COVERAGE_HTML_REPORT_NAME).toFile(), FileMatchers.anExistingFile());
    }

    @Test
    public void shouldGetFilesFromSpecifiedDirV3(){
        Path coverageDir = getDirFromResources("/api-test-coverage-v3");
        Results results = SwaggerCoverageRunner.path("classpath:petv3.feature")
                .backupReportDir(false)
                .coverageDir(coverageDir.toString())
                .oas3()
                .systemProperty("baseUrl", mock.baseUrl())
                .parallel(1);

        assertEquals(results.getErrorMessages(), 0, results.getFailCount());
        assertThat(Paths.get(COVERAGE_HTML_REPORT_NAME).toFile(), FileMatchers.anExistingFile());
    }

    @Test
    public void shouldBackupCoverageOutput() {
        Path coverageDir = getDirFromResources("/api-test-coverage-v3");
        Results results = SwaggerCoverageRunner.path("classpath:petv3.feature")
                .backupReportDir(false)
                .coverageDir(coverageDir.toString())
                .oas3()
                .systemProperty("baseUrl", mock.baseUrl())
                .parallel(1);

        assertEquals(results.getErrorMessages(), 0, results.getFailCount());
        assertThat(Paths.get(COVERAGE_HTML_REPORT_NAME).toFile(), FileMatchers.anExistingFile());

        results = SwaggerCoverageRunner.path("classpath:petv3.feature")
                .backupReportDir(false)
                .backupCoverageOutput(true)
                .coverageDir(coverageDir.toString())
                .oas3()
                .systemProperty("baseUrl", mock.baseUrl())
                .parallel(1);

        assertEquals(results.getErrorMessages(), 0, results.getFailCount());
        assertThat(Paths.get(COVERAGE_HTML_REPORT_NAME).toFile(), FileMatchers.anExistingFile());
        assertThat(Arrays.asList(coverageDir.toFile().list((dir, name) -> name.contains(SwaggerCoverageConstants.OUTPUT_DIRECTORY))), iterableWithSize(2));
    }

    private Path getDirFromResources(String name) {
        try {
            return Paths.get(this.getClass().getResource(name).toURI());
        } catch (URISyntaxException e) {
            return null;
        }
    }

    private void deleteOutputDirs(Path coverageDir){
        String[] dirs = coverageDir.toFile().list((dir, name) -> name.contains(SwaggerCoverageConstants.OUTPUT_DIRECTORY));
        for (String dir : dirs){
            FileUtils.deleteDirectory(coverageDir.resolve(dir).toFile());
        }
    }
}
