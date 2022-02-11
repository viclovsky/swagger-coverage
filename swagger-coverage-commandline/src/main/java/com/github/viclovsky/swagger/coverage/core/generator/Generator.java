package com.github.viclovsky.swagger.coverage.core.generator;

import com.github.viclovsky.swagger.coverage.CoverageOutputReader;
import com.github.viclovsky.swagger.coverage.FileSystemOutputReader;
import com.github.viclovsky.swagger.coverage.configuration.Configuration;
import com.github.viclovsky.swagger.coverage.configuration.ConfigurationBuilder;
import com.github.viclovsky.swagger.coverage.core.results.Results;
import com.github.viclovsky.swagger.coverage.core.results.builder.core.StatisticsBuilder;
import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Generator {

    private static final Logger LOGGER = LoggerFactory.getLogger(Generator.class);

    private URI specPath;
    private Path inputPath;

    private Path configurationPath;

    private OpenAPIParser parser = new OpenAPIParser();

    private List<StatisticsBuilder> statisticsBuilders = new ArrayList<>();

    public void run() {
        Configuration configuration = ConfigurationBuilder.build(configurationPath);

        SwaggerParseResult parsed = parser.readLocation(specPath.toString(), null, configuration.getParseOptions());
        parsed.getMessages().forEach(LOGGER::info);
        OpenAPI spec = parsed.getOpenAPI();

        LOGGER.info("spec is {}", spec);
        statisticsBuilders = configuration.getStatisticsBuilders(spec);

        CoverageOutputReader reader = new FileSystemOutputReader(getInputPath());
        reader.getOutputs().forEach(o -> processFile(o));

        Results result = new Results();

        statisticsBuilders.stream().filter(StatisticsBuilder::isPreBuilder).forEach(
                statisticsBuilder -> statisticsBuilder.build(result, configuration));

        statisticsBuilders.stream().filter(StatisticsBuilder::isPostBuilder).forEach(
                statisticsBuilder -> statisticsBuilder.build(result, configuration));

        configuration.getConfiguredResultsWriters().forEach(writer -> writer.write(result));
    }

    public void processFile(Path path) {
        SwaggerParseResult parsed = parser.readLocation(path.toUri().toString(), null, null);
        parsed.getMessages().forEach(LOGGER::info);
        OpenAPI spec = parsed.getOpenAPI();
        statisticsBuilders.stream().filter(StatisticsBuilder::isPreBuilder).forEach(builder ->
                builder.add(path.toString()).add(spec));
    }

    public URI getSpecPath() {
        return specPath;
    }

    public Generator setSpecPath(URI specPath) {
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
