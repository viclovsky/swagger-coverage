package com.github.viclovsky.swagger.coverage.branch.generator;

import com.github.viclovsky.swagger.coverage.CoverageOutputReader;
import com.github.viclovsky.swagger.coverage.CoverageResultsWriter;
import com.github.viclovsky.swagger.coverage.FileSystemOutputReader;
import com.github.viclovsky.swagger.coverage.branch.results.Results;
import com.github.viclovsky.swagger.coverage.branch.results.builder.core.StatisticsBuilder;
import com.github.viclovsky.swagger.coverage.branch.results.builder.postbuilder.*;
import com.github.viclovsky.swagger.coverage.branch.results.builder.prebuilder.*;
import com.github.viclovsky.swagger.coverage.branch.rule.core.BranchRule;
import com.github.viclovsky.swagger.coverage.branch.rule.parameter.*;
import com.github.viclovsky.swagger.coverage.branch.rule.status.HTTPStatusBranchRule;
import com.github.viclovsky.swagger.coverage.branch.rule.status.OnlyDeclaretedHTTPStatuses;
import com.github.viclovsky.swagger.coverage.branch.writer.HtmlBranchReportResultsWriter;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Generator {

    private static final Logger log = LoggerFactory.getLogger(Generator.class);

    private Path specPath;
    private Path inputPath;

    SwaggerParser parser = new SwaggerParser();

    List<StatisticsBuilder> statisticsBuilders = new ArrayList<>();

    public void run(){
        Swagger spec = parser.read(getSpecPath().toString());

        log.info("spec is {}",spec);

        List<BranchRule> rules = new ArrayList<>();
        rules.add(new SimpleParameterBranchRule());
        rules.add(new EmptyHeaderBranchRule());
        rules.add(new NotEmptyBodyBranchRule());
        rules.add(new HTTPStatusBranchRule());
        rules.add(new OnlyDeclaretedHTTPStatuses());
        rules.add(new EnumValuesBranchRule());
        rules.add(new NotOnlyEnumValuesBranchRule());

        statisticsBuilders.add(new CoverageStatisticsBuilder(spec,rules));
        statisticsBuilders.add(new GenerationStatisticsBuilder(spec,rules));
        statisticsBuilders.add(new BranchStatisticsBuilder(spec,rules));
        statisticsBuilders.add(new ZeroCallStatisticsBuilder(spec,rules));
        statisticsBuilders.add(new TagStatisticsBuilder(spec,rules));


        CoverageOutputReader reader = new FileSystemOutputReader(getInputPath());
        reader.getOutputs()
                .forEach(o -> processFile(o.toString()));

        CoverageResultsWriter writer = new HtmlBranchReportResultsWriter();

        Results result = new Results();

        statisticsBuilders.stream().filter(StatisticsBuilder::isPreBuilder).forEach(
            statisticsBuilder -> statisticsBuilder.build(result)
        );

        statisticsBuilders.stream().filter(StatisticsBuilder::isPostBuilder).forEach(
            statisticsBuilder -> statisticsBuilder.build(result)
        );

        writer.write(result);
    }

    public void processFile(String path) {
        final Swagger operationSwagger = parser.read(path);

        statisticsBuilders.stream().filter(StatisticsBuilder::isPreBuilder).forEach(builder ->
            builder.add(path).add(operationSwagger)
        );
    }

    public Path getSpecPath() {
        return specPath;
    }

    public Generator setSpecPath(Path specPath) {
        this.specPath = specPath;
        return this;
    }

    public Path getInputPath() {
        return inputPath;
    }

    public Generator setInputPath(Path inputPath) {
        this.inputPath = inputPath;
        return this;
    }
}
