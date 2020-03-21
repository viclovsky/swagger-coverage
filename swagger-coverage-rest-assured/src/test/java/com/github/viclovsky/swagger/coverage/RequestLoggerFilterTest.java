package com.github.viclovsky.swagger.coverage;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public class RequestLoggerFilterTest {

    private static final String BODY_STRING = "Hello world!";

    @Rule
    public WireMockRule mock = new WireMockRule(options().dynamicPort());

    @Before
    public void setUp() {
        configureFor(mock.port());
        stubFor(get(anyUrl())
                .willReturn(aResponse().withStatus(HttpStatus.SC_OK)
                        .withBody(BODY_STRING)));
    }

    @Test
    public void shouldDumpSwaggerFile() {
        RestAssured.given().filter(new SwaggerCoverageRestAssured())
                .multiPart("file", "{}")
                .header(new Header("X-Request-ID", "h"))
                .formParam("form_param", "f")
                .queryParam("query_param", "q")
                .pathParam("path_param", "p")
                .get(mock.url("/hello/{path_param}"));
    }


    @Test
    public void shouldCatchExceptionRAIssue1232() {
        RestAssured.given().filter(new SwaggerCoverageRestAssured())
                .multiPart("file", "{}")
                .header(new Header("X-Request-ID", "h"))
                .formParam("form_param", "f", "f2")
                .queryParam("query_param", "q", "q2")
                .pathParam("path_param", "p")
                .get(mock.url("/hello/{path_param}"));
    }
}
