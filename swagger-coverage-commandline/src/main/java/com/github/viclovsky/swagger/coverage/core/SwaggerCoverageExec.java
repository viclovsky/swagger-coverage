package com.github.viclovsky.swagger.coverage.core;

import com.github.viclovsky.swagger.coverage.CoverageOutputReader;
import com.github.viclovsky.swagger.coverage.CoverageResultsWriter;
import com.github.viclovsky.swagger.coverage.FileSystemOutputReader;
import com.github.viclovsky.swagger.coverage.FileSystemResultsWriter;
import com.github.viclovsky.swagger.coverage.HtmlReportResultsWriter;
import com.github.viclovsky.swagger.coverage.config.Configuration;
import com.github.viclovsky.swagger.coverage.core.filter.SwaggerCoverageFilter;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class SwaggerCoverageExec {

    private Configuration configuration;
    private List<SwaggerCoverageFilter> filters;
    private CoverageOutputReader reader;
    private List<CoverageResultsWriter> writer;

    private SwaggerCoverageExec(Configuration configuration) {
        this.configuration = configuration;
        this.filters = new ArrayList<>();
        this.reader = new FileSystemOutputReader(configuration.getOutputPath());
        this.writer = configuration.isSwaggerResults() ? Arrays.asList(new FileSystemResultsWriter()) :
                Arrays.asList(new FileSystemResultsWriter(), new HtmlReportResultsWriter());
    }

    public SwaggerCoverageExec setCoverageOutputReader(CoverageOutputReader reader) {
        this.reader = reader;
        return this;
    }

    public SwaggerCoverageExec setCoverageOutputWriter(List<CoverageResultsWriter> writer) {
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
        writer.forEach(w -> w.write(calculator.getResults()));
    }
}
