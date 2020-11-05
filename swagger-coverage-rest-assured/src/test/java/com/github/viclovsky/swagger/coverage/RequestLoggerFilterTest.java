package com.github.viclovsky.swagger.coverage;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import org.apache.http.HttpStatus;
import org.hamcrest.io.FileMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static com.github.viclovsky.swagger.coverage.SwaggerCoverageConstants.OUTPUT_DIRECTORY;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.iterableWithSize;

public class RequestLoggerFilterTest {

    private static final String BODY_STRING = "Hello world!";

    @Rule
    public WireMockRule mock = new WireMockRule(options().dynamicPort());

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private List<Path> getPaths(Path path) {
        try (Stream<Path> paths = Files.walk(path)) {
            return paths.filter(Files::isRegularFile).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("can'n walk files in swagger output directory");
        }
    }

    @Before
    public void setUp() {
        configureFor(mock.port());
        stubFor(get(anyUrl())
                .willReturn(aResponse().withStatus(HttpStatus.SC_OK)
                        .withBody(BODY_STRING)));
    }

    @Test
    public void shouldCreateDefaultOutputFolder() {
        RestAssured.given().filter(new SwaggerCoverageRestAssured())
                .get(mock.url("/hello"));
        assertThat(Paths.get(OUTPUT_DIRECTORY).toFile(), FileMatchers.anExistingDirectory());
    }

    @Test
    public void shouldDumpSwaggerFile() throws IOException {
        Path output = folder.newFolder().toPath();

        RestAssured.given().filter(new SwaggerCoverageRestAssured(new FileSystemOutputWriter(output)))
                .multiPart("file", "{}")
                .header(new Header("X-Request-ID", "h"))
                .formParam("form_param", "f")
                .queryParam("query_param", "q")
                .pathParam("path_param", "p")
                .get(mock.url("/hello/{path_param}"));

        assertThat(getPaths(output), iterableWithSize(1));
    }


    @Test
    public void shouldCatchExceptionRestAssuredIssue1232() throws IOException {
        Path output = folder.newFolder().toPath();

        RestAssured.given().filter(new SwaggerCoverageRestAssured(new FileSystemOutputWriter(output)))
                .multiPart("file", "{}")
                .header(new Header("X-Request-ID", "h"))
                .formParam("form_param", "f", "f2")
                .queryParam("query_param", "q", "q2")
                .pathParam("path_param", "p")
                .get(mock.url("/hello/{path_param}"));

        assertThat(getPaths(output), iterableWithSize(1));
    }
}
