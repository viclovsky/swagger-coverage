package com.github.viclovsky.swagger.coverage;

import io.restassured.filter.FilterContext;
import io.restassured.filter.OrderedFilter;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import io.swagger.v3.oas.models.parameters.PathParameter;
import io.swagger.v3.oas.models.parameters.QueryParameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.servers.Server;

import java.net.URI;
import java.nio.file.Paths;
import java.util.Objects;

import static com.github.viclovsky.swagger.coverage.SwaggerCoverageConstants.OUTPUT_DIRECTORY;
import static java.lang.String.valueOf;

public class SwaggerCoverageV3RestAssured implements OrderedFilter {

    private CoverageOutputWriter writer;

    public SwaggerCoverageV3RestAssured(CoverageOutputWriter writer) {
        this.writer = writer;
    }

    public SwaggerCoverageV3RestAssured() {
        this.writer = new FileSystemOutputWriter(Paths.get(OUTPUT_DIRECTORY));
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
        Operation operation = new Operation();
        requestSpec.getPathParams().forEach((n, v) -> operation.addParametersItem(new PathParameter().name(n).example(v)));
        //Ignore ClassCastException for https://github.com/rest-assured/rest-assured/issues/1232
        try {
            requestSpec.getQueryParams().forEach((n, v) -> operation.addParametersItem(new QueryParameter().name(n).example(v)));
        } catch (ClassCastException ex) {
            requestSpec.getQueryParams().keySet().forEach(n -> operation.addParametersItem(new QueryParameter().name(n)));
        }
        requestSpec.getHeaders().forEach(header -> operation.addParametersItem(new HeaderParameter().name(header.getName())
                .example(header.getValue())));

        final Response response = ctx.next(requestSpec, responseSpec);

        if (Objects.nonNull(requestSpec.getBody())) {
            MediaType mediaType = new MediaType();
            mediaType.setSchema(new Schema());
            //Ignore ClassCastException for https://github.com/rest-assured/rest-assured/issues/1232
            try {
                requestSpec.getFormParams().forEach((n, v) -> mediaType.getSchema().addProperties(n, new Schema().example(v)));
            } catch (ClassCastException ex) {
                requestSpec.getFormParams().keySet().forEach((n -> mediaType.getSchema().addProperties(n, new Schema())));
            }
            requestSpec.getMultiPartParams().forEach(multiPartSpecification -> mediaType.getSchema().addProperties(multiPartSpecification.getControlName(), new Schema()));
            operation.requestBody(
                    new RequestBody().content(new Content().addMediaType(requestSpec.getContentType(), mediaType)));

        }

        operation.responses(new ApiResponses()
                .addApiResponse(valueOf(response.statusCode()),
                        new ApiResponse().content(new Content().addMediaType(response.getContentType(), new MediaType()))));

        PathItem pathItem = new PathItem();
        pathItem.operation(PathItem.HttpMethod.valueOf(requestSpec.getMethod().toUpperCase()), operation);
        OpenAPI openAPI = new OpenAPI()
                .addServersItem(new Server().url(URI.create(requestSpec.getURI()).getHost()))
                .path(requestSpec.getUserDefinedPath(), pathItem);

        writer.write(openAPI);
        return response;
    }
}