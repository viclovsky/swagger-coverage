package ru.viclovsky.swagger.coverage.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;
import org.apache.log4j.Logger;
import ru.viclovsky.swagger.coverage.config.Config;
import ru.viclovsky.swagger.coverage.model.Coverage;
import ru.viclovsky.swagger.coverage.model.Output;
import ru.viclovsky.swagger.coverage.model.Problem;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

public class SwaggerCoverageExec {

    private static final String COVERAGE_RESULTS_FILE_SUFFIX = "-coverage-results.json";

    private Config config;


    private SwaggerCoverageExec(Config config) {
        this.config = config;
    }

    public static SwaggerCoverageExec swaggerCoverage(Config config) {
        return new SwaggerCoverageExec(config);
    }

    private final static Logger LOG = Logger.getLogger(SwaggerCoverageExec.class);

    public void execute() {
        SwaggerParser parser = new SwaggerParser();
        Swagger spec = new SwaggerParser().read(config.getSpecPath().toString());
        //todo: copy object?
        Swagger temp = new SwaggerParser().read(config.getSpecPath().toString());

        Map<Path, Swagger> input = new HashMap<>();
        readPaths(config.getInputPath()).forEach(p -> input.put(p, parser.read(p.toString())));

        Compare compare = new Compare(spec, temp);
        compare.addCoverage(input.values());
        Coverage coverage = compare.getCoverage();
        Output output = printCoverage(coverage);
        writeInFile(dumpToJson(output));
    }

    private Output printCoverage(Coverage coverage) {
        Output output = new Output();
        int emptyCount = coverage.getEmpty().size();
        int partialCount = coverage.getPartial().size();
        int fullCount = coverage.getFull().size();
        int allCount = emptyCount + partialCount + fullCount;

        Map<String, Problem> partialOutput = new HashMap<>();

        coverage.getPartial().forEach((k, v) ->
        {
            Problem problem = new Problem();
            Set<String> paramsProblem = new TreeSet<>();
            Set<String> statusCodesProblem = new TreeSet<>();

            v.getModified().getParameters().forEach(parameter ->
                    paramsProblem.add(parameter.getName()));
            v.getModified().getResponses().forEach((status, resp) ->
                    statusCodesProblem.add(status));

            partialOutput.put(k, problem
                    .withAllParamsCount(v.getOriginal().getParameters().size())
                    .withParamsCount(v.getModified().getParameters().size())
                    .withAllStatusCodesCount(v.getOriginal().getResponses().keySet().size())
                    .withStatusCodesCount(v.getModified().getResponses().keySet().size())
                    .withParams(paramsProblem)
                    .withStatusCodes(statusCodesProblem)
            );
        });

        output.withAllCount(allCount).withEmptyCount(emptyCount)
                .withPartialCount(partialCount)
                .withFullCount(fullCount)
                .withEmpty(coverage.getEmpty().keySet())
                .withFull(coverage.getFull().keySet())
                .withPartial(partialOutput);

        return output;
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
        String fileName = UUID.randomUUID().toString() + COVERAGE_RESULTS_FILE_SUFFIX;

        try (FileWriter fileWriter = new FileWriter(fileName, true)) {
            fileWriter.write(json);
        } catch (IOException e) {
            throw new RuntimeException("Could not write Swagger coverage in file", e);
        }

        return Paths.get(fileName);
    }
}
