package com.github.viclovsky.swagger.coverage;

import static io.swagger.models.Scheme.forValue;

import java.net.URI;
import java.util.List;
import java.util.Map;

import io.swagger.models.*;
import io.swagger.models.parameters.*;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

public class RequestWriter {
    
    private CoverageOutputWriter writer;

    public RequestWriter(String workingDir){
        writer = new FileSystemOutputWriter(java.nio.file.Path.of(workingDir, SwaggerCoverageConstants.OUTPUT_DIRECTORY));
    }

    public void write(Request request, Boolean oas3){
        if (oas3){
            writeOAS3(request);
        }
        else{
            writeSwagger(request);
        }
    }

    public void writeSwagger(Request request) {
        Map<String, List<String>> headerParams = request.getHeaderParams();
        Map<String, List<String>> requestParams = request.getRequestParams();
        Map<String, List<Map<String, String>>> requestParts = request.getRequestParts();
        Map<String, List<String>> responseHeaders = request.getResponseHeaders();
        Operation operation = new Operation();

        headerParams.forEach((n, v) -> operation.addParameter(new HeaderParameter().name(n)));

        if (request.hasBody()) {
            operation.addConsumes(getContentType(headerParams));
            operation.addParameter(new BodyParameter().name("body"));
            requestParams.forEach((n, v) -> operation.addParameter(new FormParameter().name(n)));

            if (requestParts != null) {
                requestParts.forEach((n, v) -> operation.addParameter(new FormParameter().name(n)));
            }
        } else {
            requestParams.forEach((n, v) -> operation.addParameter(new QueryParameter().name(n)));
        }

        if (responseHeaders.containsKey("content-type")) {
            operation.addProduces(getContentType(responseHeaders));
        }

        operation.addResponse(Integer.toString(request.getStatusCode()), new Response());

        URI uri = URI.create(request.getBaseUrl());
        String path = "/" + trimAfterChar(request.getPath(), "?");

        Swagger swagger = new Swagger().scheme(forValue(uri.getScheme())).host(uri.getHost()).path(path,
                new Path().set(request.getMethod().toLowerCase(), operation));

        writer.write(swagger);
    }

    public void writeOAS3(Request request){
        Map<String, List<String>> headerParams = request.getHeaderParams();
        Map<String, List<String>> requestParams = request.getRequestParams();
        Map<String, List<Map<String, String>>> requestParts = request.getRequestParts();
        Map<String, List<String>> responseHeaders = request.getResponseHeaders();
        io.swagger.v3.oas.models.Operation operation = new io.swagger.v3.oas.models.Operation();

        headerParams.forEach((n, v) -> operation
                .addParametersItem(new io.swagger.v3.oas.models.parameters.HeaderParameter().name(n).example(v)));

        if (request.hasBody()) {
            MediaType mediaType = new MediaType();
            Schema<Object> schema = new Schema<>();
            requestParams.forEach((n, v) -> schema.addProperties(n, new Schema<>().example(v)));

            if (requestParts != null) {
                requestParts.forEach((n, v) -> schema.addProperties(n, new Schema<>()));
            }

            mediaType.setSchema(schema);
            operation.requestBody(
                    new RequestBody().content(new Content().addMediaType(getContentType(headerParams), mediaType)));
        } else {
            requestParams.forEach((n, v) -> operation
                    .addParametersItem(new io.swagger.v3.oas.models.parameters.QueryParameter().name(n).example(v)));
        }

        operation.responses(new ApiResponses().addApiResponse(Integer.toString(request.getStatusCode()),
                new ApiResponse()
                        .content(new Content().addMediaType(getContentType(responseHeaders), new MediaType()))));

        URI uri = URI.create(request.getBaseUrl());
        String path = "/" + trimAfterChar(request.getPath(), "?");

        PathItem pathItem = new PathItem();
        pathItem.operation(PathItem.HttpMethod.valueOf(request.getMethod().toUpperCase()), operation);

        OpenAPI openAPI = new OpenAPI().addServersItem(new Server().url(uri.getHost()))
                .path(path, pathItem);

        writer.write(openAPI);
    }

    private String getContentType(Map<String, List<String>> headerList) {
        if (!headerList.containsKey("content-type")) {
            return "";
        }

        String contentType = headerList.get("content-type").get(0);
        return trimAfterChar(contentType, ";");
    }

    private String trimAfterChar(String s, String character) {
        return s.substring(0, s.contains(character) ? s.lastIndexOf(character) : s.length());
    }
}
