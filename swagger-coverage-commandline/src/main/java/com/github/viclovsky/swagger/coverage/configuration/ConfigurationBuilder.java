package com.github.viclovsky.swagger.coverage.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.viclovsky.swagger.coverage.configuration.options.ConfigurationOptions;
import com.github.viclovsky.swagger.coverage.core.results.builder.core.StatisticsBuilder;
import com.github.viclovsky.swagger.coverage.core.results.builder.postbuilder.ConditionStatisticsBuilder;
import com.github.viclovsky.swagger.coverage.core.results.builder.postbuilder.ConfigurationStatisticsBuilder;
import com.github.viclovsky.swagger.coverage.core.results.builder.postbuilder.FlatOperationBuilder;
import com.github.viclovsky.swagger.coverage.core.results.builder.postbuilder.SwaggerInfoBuilder;
import com.github.viclovsky.swagger.coverage.core.results.builder.postbuilder.TagStatisticsBuilder;
import com.github.viclovsky.swagger.coverage.core.results.builder.postbuilder.ZeroCallStatisticsBuilder;
import com.github.viclovsky.swagger.coverage.core.results.builder.prebuilder.CoverageStatisticsBuilder;
import com.github.viclovsky.swagger.coverage.core.results.builder.prebuilder.GenerationStatisticsBuilder;
import com.github.viclovsky.swagger.coverage.core.rule.core.ConditionRule;
import com.github.viclovsky.swagger.coverage.core.rule.parameter.EmptyHeaderRule;
import com.github.viclovsky.swagger.coverage.core.rule.parameter.EnumAllValuesRule;
import com.github.viclovsky.swagger.coverage.core.rule.parameter.NotEmptyBodyRule;
import com.github.viclovsky.swagger.coverage.core.rule.parameter.NotEmptyParameterRule;
import com.github.viclovsky.swagger.coverage.core.rule.parameter.NotOnlyEnumValuesRule;
import com.github.viclovsky.swagger.coverage.core.rule.status.HTTPStatusRule;
import com.github.viclovsky.swagger.coverage.core.rule.status.OnlyDeclaredHTTPStatusesRule;
import com.github.viclovsky.swagger.coverage.core.writer.CoverageResultsWriter;
import com.github.viclovsky.swagger.coverage.core.writer.FileSystemResultsWriter;
import com.github.viclovsky.swagger.coverage.core.writer.HtmlReportResultsWriter;
import com.github.viclovsky.swagger.coverage.core.writer.LogResultsWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ConfigurationBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationBuilder.class);

    public static Configuration build(Path path) {
        Configuration configuration = new Configuration();
        ConfigurationOptions options = new ConfigurationOptions();
        ObjectMapper mapper = new ObjectMapper();
        try {
            if (path != null) {
                options = mapper.readValue(path.toFile(), ConfigurationOptions.class);
            }
        } catch (IOException e) {
            LOGGER.info("can't read configuration, use default configuration");
        }
        configuration.setOptions(options)
                .setDefaultRules(getDefaultList())
                .setRegisteredBuilders(getDefaultBuilderList())
                .setConfiguredResultsWriters(getResultsWriters(options));

        return configuration;
    }

    private static List<CoverageResultsWriter> getResultsWriters(ConfigurationOptions options) {
        List<CoverageResultsWriter> configuredResultsWriters = new ArrayList<>();

        if (options.getWriters().isEmpty()) {
            configuredResultsWriters.add(new HtmlReportResultsWriter());
            configuredResultsWriters.add(new LogResultsWriter());
            configuredResultsWriters.add(new FileSystemResultsWriter());
        } else {
            options.getWriters()
                    .forEach((key, value) -> {
                        switch (key) {
                            case "html":
                                configuredResultsWriters.add(
                                        new HtmlReportResultsWriter(value.getLocale(), value.getFilename())
                                );
                                break;
                            case "LOGGER":
                                configuredResultsWriters.add(
                                        new LogResultsWriter()
                                );
                                break;
                            case "json":
                                configuredResultsWriters.add(
                                        new FileSystemResultsWriter()
                                );
                                break;
                        }
                    });
        }

        return configuredResultsWriters;
    }

    private static List<ConditionRule> getDefaultList() {
        List<ConditionRule> registeredRules = new ArrayList<>();

        registeredRules.add(new HTTPStatusRule());
        registeredRules.add(new NotEmptyParameterRule());
        registeredRules.add(new EnumAllValuesRule());
        registeredRules.add(new NotEmptyBodyRule());
        registeredRules.add(new OnlyDeclaredHTTPStatusesRule());
        registeredRules.add(new EmptyHeaderRule());
        registeredRules.add(new NotOnlyEnumValuesRule());
        return registeredRules;
    }

    private static List<StatisticsBuilder> getDefaultBuilderList() {
        List<StatisticsBuilder> registeredBuilders = new ArrayList<>();

        registeredBuilders.add(new CoverageStatisticsBuilder());
        registeredBuilders.add(new GenerationStatisticsBuilder());
        registeredBuilders.add(new ConditionStatisticsBuilder());
        registeredBuilders.add(new ZeroCallStatisticsBuilder());
        registeredBuilders.add(new TagStatisticsBuilder());
        registeredBuilders.add(new ConfigurationStatisticsBuilder());
        registeredBuilders.add(new FlatOperationBuilder());
        registeredBuilders.add(new SwaggerInfoBuilder());

        return registeredBuilders;
    }
}
