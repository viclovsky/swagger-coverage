package ru.viclovsky.swagger.coverage;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import ru.viclovsky.swagger.coverage.model.SwaggerCoverage2ModelJackson;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.CREATE_NEW;
import static ru.viclovsky.swagger.coverage.SwaggerCoverageUtils.generateCoverageResultsName;

public class FileSystemResultsWriter implements CoverageResultsWriter {

    private final static Logger LOGGER = Logger.getLogger(FileSystemResultsWriter.class);
    private final ObjectMapper mapper;

    public FileSystemResultsWriter() {
        this.mapper = SwaggerCoverage2ModelJackson.createMapper();
    }

    @Override
    public void write(Object results) {
        final String swaggerResultName = generateCoverageResultsName();
        Path path = Paths.get(swaggerResultName);
        LOGGER.info(String.format("Write results in file '%s'", path.toAbsolutePath()));
        try (OutputStream os = Files.newOutputStream(Paths.get(swaggerResultName), CREATE_NEW)) {
            mapper.writeValue(os, results);
        } catch (IOException e) {
            throw new SwaggerCoverageWriteException("Could not write results", e);
        }
    }
}
