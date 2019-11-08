package ru.viclovsky.swagger.coverage;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.restassured.filter.FilterContext;
import io.restassured.filter.OrderedFilter;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import io.swagger.models.Operation;
import io.swagger.models.Swagger;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.FormParameter;
import io.swagger.models.parameters.HeaderParameter;
import io.swagger.models.parameters.PathParameter;
import io.swagger.models.parameters.QueryParameter;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

import static io.swagger.models.Scheme.forValue;
import static java.lang.String.valueOf;

public class RequestLoggerFilter implements OrderedFilter {

    private static final String OUTPUT_DIRECTORY = "swagger-coverage-output";
    private static final String COVERAGE_RESULT_FILE_SUFFIX = "-coverage.json";
    private Path outputDirectory = Paths.get(OUTPUT_DIRECTORY);

    public RequestLoggerFilter(Path outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public RequestLoggerFilter() {
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
        Operation operation = new Operation();
        requestSpec.getPathParams().forEach((n, v) -> operation.addParameter(new PathParameter().name(n).example(v)));
        requestSpec.getQueryParams().forEach((n, v) -> operation.addParameter(new QueryParameter().name(n).example(v)));
        requestSpec.getFormParams().forEach((n, v) -> operation.addParameter(new FormParameter().name(n).example(v)));
        requestSpec.getHeaders().forEach(header -> operation.addParameter(new HeaderParameter().name(header.getName()).example(header.getValue())));

        if (Objects.nonNull(requestSpec.getBody())) {
            operation.addParameter(new BodyParameter().name("body"));
        }

        final Response response = ctx.next(requestSpec, responseSpec);

        operation.addResponse(valueOf(response.statusCode()), new io.swagger.models.Response());

        Swagger swagger = new Swagger()
                .scheme(forValue(URI.create(requestSpec.getURI()).getScheme()))
                .host(URI.create(requestSpec.getURI()).getHost())
                .consumes(requestSpec.getContentType())
                .produces(response.getContentType())
                .path(requestSpec.getUserDefinedPath(), new io.swagger.models.Path().set(requestSpec.getMethod().toLowerCase(), operation));

        createDirectories(outputDirectory);
        writeInFile(swagger);
        return response;
    }

    private void createDirectories(final Path directory) {
        try {
            Files.createDirectories(directory);
        } catch (IOException e) {
            throw new RuntimeException("Could not create Swagger coverage directory", e);
        }
    }

    private void writeInFile(Swagger swagger) {
        //dump to json
        ObjectWriter ow = new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .writer().withDefaultPrettyPrinter();
        String json = null;

        try {
            json = ow.writeValueAsString(swagger);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not process Swagger coverage", e);

        }

        String uuid = UUID.randomUUID().toString() + COVERAGE_RESULT_FILE_SUFFIX;
        //write in uuid random file
        try (FileWriter fileWriter = new FileWriter(Paths.get(outputDirectory.toString(), uuid).toFile(), true)) {
            fileWriter.write(json);
        } catch (IOException e) {
            throw new RuntimeException("Could not write Swagger coverage in file", e);
        }
    }
}