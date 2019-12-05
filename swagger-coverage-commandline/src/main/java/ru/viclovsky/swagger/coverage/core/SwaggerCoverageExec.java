package ru.viclovsky.swagger.coverage.core;

import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;
import ru.viclovsky.swagger.coverage.CoverageOutputReader;
import ru.viclovsky.swagger.coverage.CoverageResultsWriter;
import ru.viclovsky.swagger.coverage.FileSystemOutputReader;
import ru.viclovsky.swagger.coverage.FileSystemResultsWriter;
import ru.viclovsky.swagger.coverage.config.Configuration;
import ru.viclovsky.swagger.coverage.core.filter.SwaggerCoverageFilter;

import java.util.*;

public final class SwaggerCoverageExec {

    private Configuration configuration;
    private List<SwaggerCoverageFilter> filters;
    private CoverageOutputReader reader;
    private CoverageResultsWriter writer;

    private SwaggerCoverageExec(Configuration configuration) {
        this.configuration = configuration;
        this.filters = new ArrayList<>();
        this.reader = new FileSystemOutputReader(configuration.getOutputPath());
        this.writer = new FileSystemResultsWriter();
    }

    public SwaggerCoverageExec setCoverageOutputReader(CoverageOutputReader reader) {
        this.reader = reader;
        return this;
    }

    public SwaggerCoverageExec setCoverageOutputWriter(CoverageResultsWriter writer) {
        this.writer = writer;
        return this;
    }

    public SwaggerCoverageExec setFilters(List<SwaggerCoverageFilter> filters) {
        this.filters = filters;
        return this;
    }

    public static SwaggerCoverageExec swaggerCoverage(Configuration configuration) {
        return new SwaggerCoverageExec(configuration);
    }

    public void execute() {
        SwaggerParser parser = new SwaggerParser();
        Swagger spec = parser.read(configuration.getSpecPath().toString());
        final SwaggerCoverageCalculator calculator = configuration.isSwaggerResults()
                ? new DefaultSwaggerCoverageCalculator(filters, spec)
                : new OperationSwaggerCoverageCalculator(filters, spec);
        reader.getOutputs()
                .forEach(o -> calculator.addOutput(parser.read(o.toString())));
        writer.write(calculator.getResults());
    }
}
