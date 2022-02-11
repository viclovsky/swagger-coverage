package com.github.viclovsky.swagger.coverage.core.writer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.viclovsky.swagger.coverage.SwaggerCoverageWriteException;
import com.github.viclovsky.swagger.coverage.core.results.Results;
import com.github.viclovsky.swagger.coverage.model.SwaggerCoverage2ModelJackson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.github.viclovsky.swagger.coverage.SwaggerCoverageConstants.COVERAGE_RESULTS_NAME;

public class FileSystemResultsWriter implements CoverageResultsWriter {

    private final static Logger LOGGER = LoggerFactory.getLogger(FileSystemResultsWriter.class);
    private final ObjectMapper mapper;
    private final String fileName;

    public FileSystemResultsWriter() {
        this(COVERAGE_RESULTS_NAME);
    }

    public FileSystemResultsWriter(String fileName) {
        this.fileName = fileName;
        this.mapper = SwaggerCoverage2ModelJackson.createJsonMapper();
    }

    @Override
    public void write(Results results) {
        final String swaggerResultName = fileName;
        Path path = Paths.get(swaggerResultName);
        LOGGER.info(String.format("Write results in file '%s'", path.toAbsolutePath()));
        try (OutputStream os = Files.newOutputStream(Paths.get(swaggerResultName))) {
            mapper.writeValue(os, results);
        } catch (IOException e) {
            throw new SwaggerCoverageWriteException("Could not write results", e);
        }
    }
}
