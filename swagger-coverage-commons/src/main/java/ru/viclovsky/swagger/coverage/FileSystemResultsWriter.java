package ru.viclovsky.swagger.coverage;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.models.Swagger;
import ru.viclovsky.swagger.coverage.model.SwaggerCoverage2ModelJackson;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;
import static ru.viclovsky.swagger.coverage.SwaggerCoverageUtils.generateCoverageResultName;

public class FileSystemResultsWriter implements CoverageResultsWriter {

    private final Path outputDirectory;

    private final ObjectMapper mapper;

    public FileSystemResultsWriter(final Path outputDirectory) {
        this.outputDirectory = outputDirectory;
        this.mapper = SwaggerCoverage2ModelJackson.createMapper();
    }

    private void createDirectories(final Path directory) {
        try {
            Files.createDirectories(directory);
        } catch (IOException e) {
            throw new SwaggerCoverageWriteException("Could not create Swagger results directory", e);
        }
    }

    @Override
    public void write(Swagger swaggerResults) {
        final String swaggerResultName = generateCoverageResultName();
        createDirectories(outputDirectory);
        Path file = outputDirectory.resolve(swaggerResultName);
        try (OutputStream os = Files.newOutputStream(file, CREATE_NEW)) {
            mapper.writeValue(os, swaggerResults);
        } catch (IOException e) {
            throw new SwaggerCoverageWriteException("Could not write Swagger result", e);
        }
    }
}
