package ru.viclovsky.swagger.coverage;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;
import ru.viclovsky.swagger.coverage.core.OperationSwaggerCoverageCalculator;
import ru.viclovsky.swagger.coverage.core.filter.IgnoreParamsFilter;
import ru.viclovsky.swagger.coverage.core.filter.SwaggerCoverageFilter;
import ru.viclovsky.swagger.coverage.model.SwaggerCoverageResults;
import ru.viclovsky.swagger.coverage.utils.FreemarkerUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static io.swagger.models.ParamType.HEADER;

public class Main {

    private final static Path SCHEMA = Paths.get("/Users/eroshenkoam/Downloads/autoru-swagger.json").toAbsolutePath();
    private final static Path TEST_RESULTS = Paths.get("/Users/eroshenkoam/Downloads/swagger-coverage-autoru").toAbsolutePath();

    public static void main(String[] args) throws Exception {
        final SwaggerParser parser = new SwaggerParser();
        final Swagger spec = parser.read(SCHEMA.toString());

        final List<SwaggerCoverageFilter> filters = new ArrayList<>();
        filters.add(new IgnoreParamsFilter(Arrays.asList("x-uid", "x-features", "x-exp-flags", "x-device-uid"), HEADER));

        final OperationSwaggerCoverageCalculator calculator =
                new OperationSwaggerCoverageCalculator(filters, spec);

        final List<String> testSwaggers = Files.walk(TEST_RESULTS)
                .filter(Files::isRegularFile)
                .map(Path::toAbsolutePath)
                .map(Path::toString)
                .collect(Collectors.toList());

        testSwaggers.stream()
                .map(parser::read)
                .forEach(calculator::addOutput);

        SwaggerCoverageResults results = (SwaggerCoverageResults) calculator.getResults();
        final String jsonReport = new ObjectMapper().writeValueAsString(results);
        Files.write(Paths.get("./report.json"), jsonReport.getBytes());

        final String htmlReport = FreemarkerUtils.processTemplate("report.ftl", results);
        Files.write(Paths.get("./report.html"), htmlReport.getBytes());

    }

}
