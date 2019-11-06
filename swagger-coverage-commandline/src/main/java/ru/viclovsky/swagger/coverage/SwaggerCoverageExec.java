package ru.viclovsky.swagger.coverage;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;
import org.apache.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

public class SwaggerCoverageExec {

    private static final String OUTPUT_DIRECTORY = "swagger-coverage-results";
    private static final String COVERAGE_RESULTS_FILE_SUFFIX = "-coverage-results.json";

    private Config config;


    private SwaggerCoverageExec(Config config) {
        this.config = config;
    }

    public static SwaggerCoverageExec swaggerCoverage(Config config) {
        return new SwaggerCoverageExec(config);
    }

    private final static Logger LOG = Logger.getLogger(SwaggerCoverageExec.class);

    void execute() {
        if (!isValidResultsDirectory(config.getOutputPath())) {
            LOG.error(String.format("%s not valid directory", config.getOutputPath()));
            config.withOutputPath(Paths.get(OUTPUT_DIRECTORY));
        }
        createDirectories(config.getOutputPath());

        SwaggerParser parser = new SwaggerParser();
        Swagger spec = new SwaggerParser().read(config.getSpecPath().toString());

        Map<Path, Swagger> coverage = new HashMap<>();
        readPaths(config.getReqPath()).forEach(p -> coverage.put(p, parser.read(p.toString())));

        Compare compare = new Compare(spec);
        coverage.forEach((p, s) -> compare.addCoverage(s));
        String json = dumpToJson(compare.getCoverage());
        writeInFile(json);
    }

    private Set<Path> readPaths(Path output) {
        Set<Path> result = new HashSet<>();
        try (Stream<Path> paths = Files.walk(output)) {
            paths.filter(Files::isRegularFile).forEach(result::add);
        } catch (IOException e) {
            throw new RuntimeException("can't read coverage file's", e);
        }
        return result;
    }

    private boolean isValidResultsDirectory(final Path resultsDirectory) {
        if (isNull(resultsDirectory)) {
            return false;
        }
        if (Files.notExists(resultsDirectory)) {
            return false;
        }
        return Files.isDirectory(resultsDirectory);
    }

    private void createDirectories(final Path directory) {
        try {
            Files.createDirectories(directory);
        } catch (IOException e) {
            throw new RuntimeException("Could not create Swagger coverage directory", e);
        }
    }

    //todo: remove this
    private String dumpToJson(Object o) {
        //dump to json
        ObjectWriter ow = new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .writer().withDefaultPrettyPrinter();
        String json = null;

        try {
            json = ow.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not process json", e);
        }
        return json;
    }

    private Path writeInFile(String json) {
        String uuid = UUID.randomUUID().toString() + COVERAGE_RESULTS_FILE_SUFFIX;
        Path path = Paths.get(config.getOutputPath().toString(), uuid);

        try (FileWriter fileWriter =  new FileWriter(path.toFile(), true)) {
            fileWriter.write(json);
        } catch (IOException e) {
            throw new RuntimeException("Could not write Swagger coverage in file", e);
        }

        return path;
    }
}
