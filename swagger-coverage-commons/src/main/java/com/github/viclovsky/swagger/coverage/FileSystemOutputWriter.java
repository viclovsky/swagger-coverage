package com.github.viclovsky.swagger.coverage;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.models.Swagger;
import com.github.viclovsky.swagger.coverage.model.SwaggerCoverage2ModelJackson;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;
import static com.github.viclovsky.swagger.coverage.SwaggerCoverageUtils.generateCoverageOutputName;

public class FileSystemOutputWriter implements CoverageOutputWriter {

    private final Path outputDirectory;

    private final ObjectMapper mapper;

    public FileSystemOutputWriter(final Path outputDirectory) {
        this.outputDirectory = outputDirectory;
        this.mapper = SwaggerCoverage2ModelJackson.createMapper();
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
        final String swaggerResultName = generateCoverageOutputName();
        createDirectories(outputDirectory);
        Path file = outputDirectory.resolve(swaggerResultName);
        try (OutputStream os = Files.newOutputStream(file, CREATE_NEW)) {
            mapper.writeValue(os, swagger);
        } catch (IOException e) {
            throw new SwaggerCoverageWriteException("Could not write Swagger", e);
        }
    }
}
