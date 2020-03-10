package com.github.viclovsky.swagger.coverage;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.net.URI;
import java.nio.file.Paths;
import java.util.Objects;

import static io.swagger.models.Scheme.forValue;
import static java.lang.String.valueOf;
import static com.github.viclovsky.swagger.coverage.SwaggerCoverageConstants.BODY_PARAM_NAME;
import static com.github.viclovsky.swagger.coverage.SwaggerCoverageConstants.OUTPUT_DIRECTORY;

public class SwaggerCoverageRestAssured implements OrderedFilter {

    private CoverageOutputWriter writer;

    private ObjectMapper mapper;

    public SwaggerCoverageRestAssured(CoverageOutputWriter writer) {
        this.writer = writer;
    }

    public SwaggerCoverageRestAssured() {
        this.writer = new FileSystemOutputWriter(Paths.get(OUTPUT_DIRECTORY));
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }

    protected ObjectMapper getMapper(){
        if (mapper == null) {
            mapper = new ObjectMapper();
        }

        return mapper;
    }

    protected String writeAsJSON(Object object){
        try {
            return getMapper().writeValueAsString(object);
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            return "";
        }
    }

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
        Operation operation = new Operation();
        requestSpec.getPathParams().forEach((n, v) -> operation.addParameter(new PathParameter().name(n).example(v)));
        //https://github.com/rest-assured/rest-assured/issues/1232
        requestSpec.getQueryParams().keySet().forEach(n -> operation.addParameter(new QueryParameter().name(n)));
        requestSpec.getFormParams().keySet().forEach((n -> operation.addParameter(new FormParameter().name(n))));
        requestSpec.getHeaders().forEach(header -> operation.addParameter(new HeaderParameter().name(header.getName()).example(header.getValue())));

        requestSpec.getMultiPartParams().forEach(multiPartSpecification ->
            operation.addParameter(
                new FormParameter()
                    .name(multiPartSpecification.getControlName())
                    .example(writeAsJSON(multiPartSpecification))
            )
        );

        if (Objects.nonNull(requestSpec.getBody())) {
            operation.addParameter(new BodyParameter().name(BODY_PARAM_NAME));
        }

        final Response response = ctx.next(requestSpec, responseSpec);

        operation.addResponse(valueOf(response.statusCode()), new io.swagger.models.Response());

        Swagger swagger = new Swagger()
                .scheme(forValue(URI.create(requestSpec.getURI()).getScheme()))
                .host(URI.create(requestSpec.getURI()).getHost())
                .consumes(requestSpec.getContentType())
                .produces(response.getContentType())
                .path(requestSpec.getUserDefinedPath(), new io.swagger.models.Path().set(requestSpec.getMethod().toLowerCase(), operation));

        writer.write(swagger);
        return response;
    }
}