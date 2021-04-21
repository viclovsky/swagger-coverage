package com.github.viclovsky.swagger.coverage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.viclovsky.swagger.coverage.model.SwaggerCoverage2ModelJackson;
import io.swagger.models.Swagger;
import io.swagger.v3.oas.models.OpenAPI;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.github.viclovsky.swagger.coverage.SwaggerCoverageUtils.generateJsonCoverageOutputName;
import static com.github.viclovsky.swagger.coverage.SwaggerCoverageUtils.generateYamlCoverageOutputName;
import static java.nio.file.StandardOpenOption.CREATE_NEW;

public class FileSystemOutputWriter implements CoverageOutputWriter {

    private final Path outputDirectory;

    private final ObjectMapper jsonMapper;
    private final ObjectMapper yamlMapper;

    public FileSystemOutputWriter(final Path outputDirectory) {
        this.outputDirectory = outputDirectory;
        this.jsonMapper = SwaggerCoverage2ModelJackson.createJsonMapper();
        this.yamlMapper = SwaggerCoverage2ModelJackson.createYamlMapper();
    }

    private void createDirectories(final Path directory) {
        try {
            Files.createDirectories(directory);
        } catch (IOException e) {
            throw new SwaggerCoverageWriteException("Could not create Swagger output directory", e);
        }
    }

    @Override
    public void write(Swagger swagger) {
        final String swaggerResultName = generateJsonCoverageOutputName();
        createDirectories(outputDirectory);
        Path file = outputDirectory.resolve(swaggerResultName);
        try (OutputStream os = Files.newOutputStream(file, CREATE_NEW)) {
            jsonMapper.writerWithDefaultPrettyPrinter().writeValue(os, swagger);
        } catch (IOException e) {
            throw new SwaggerCoverageWriteException("Could not write Swagger", e);
        }
    }

    @Override
    public void write(OpenAPI openAPI) {
        final String swaggerResultName = generateYamlCoverageOutputName();
        createDirectories(outputDirectory);
        Path file = outputDirectory.resolve(swaggerResultName);
        try (OutputStream os = Files.newOutputStream(file, CREATE_NEW)) {
            yamlMapper.writerWithDefaultPrettyPrinter().writeValue(os, openAPI);
        } catch (IOException e) {
            throw new SwaggerCoverageWriteException("Could not write Swagger", e);
        }
    }

}
