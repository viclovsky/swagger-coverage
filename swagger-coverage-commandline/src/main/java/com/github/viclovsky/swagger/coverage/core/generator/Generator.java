package com.github.viclovsky.swagger.coverage.core.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.viclovsky.swagger.coverage.CoverageOutputReader;
import com.github.viclovsky.swagger.coverage.FileSystemOutputReader;
import com.github.viclovsky.swagger.coverage.configuration.Configuration;
import com.github.viclovsky.swagger.coverage.configuration.ConfigurationBuilder;
import com.github.viclovsky.swagger.coverage.core.results.Results;
import com.github.viclovsky.swagger.coverage.core.results.builder.core.StatisticsBuilder;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Generator {

    private static final Logger log = LoggerFactory.getLogger(Generator.class);

    private Path specPath;
    private Path inputPath;

    private Path configurationPath;

    SwaggerParser parser = new SwaggerParser();

    List<StatisticsBuilder> statisticsBuilders = new ArrayList<>();

    public void run() {
        Swagger spec = parser.read(getSpecPath().toString());

        log.info("spec is {}",spec);

        Configuration configuration = ConfigurationBuilder.build(configurationPath);
        statisticsBuilders = configuration.getStatisticsBuilders(spec);

        CoverageOutputReader reader = new FileSystemOutputReader(getInputPath());
        reader.getOutputs()
                .forEach(o -> processFile(o.toString()));

        Results result = new Results();

        statisticsBuilders.stream().filter(StatisticsBuilder::isPreBuilder).forEach(
                statisticsBuilder -> statisticsBuilder.build(result)
        );

        statisticsBuilders.stream().filter(StatisticsBuilder::isPostBuilder).forEach(
                statisticsBuilder -> statisticsBuilder.build(result)
        );

        System.out.println(configuration.getConfiguredResultsWriters().toString());


        configuration
                .getConfiguredResultsWriters()
                .stream()
                .forEach(
                        writer -> writer.write(result)
                )
        ;
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

    public Path getConfigurationPath() {
        return configurationPath;
    }

    public Generator setConfigurationPath(Path configurationPath) {
        this.configurationPath = configurationPath;
        return this;
    }
}
