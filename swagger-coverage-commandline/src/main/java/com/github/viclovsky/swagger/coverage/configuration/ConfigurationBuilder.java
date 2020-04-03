package com.github.viclovsky.swagger.coverage.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.viclovsky.swagger.coverage.CoverageResultsWriter;
import com.github.viclovsky.swagger.coverage.HtmlBranchReportResultsWriter;
import com.github.viclovsky.swagger.coverage.configuration.options.ConfigurationOptions;
import com.github.viclovsky.swagger.coverage.core.results.builder.core.StatisticsBuilder;
import com.github.viclovsky.swagger.coverage.core.results.builder.postbuilder.*;
import com.github.viclovsky.swagger.coverage.core.results.builder.prebuilder.*;
import com.github.viclovsky.swagger.coverage.core.rule.core.ConditionRule;
import com.github.viclovsky.swagger.coverage.core.rule.parameter.*;
import com.github.viclovsky.swagger.coverage.core.rule.status.*;


import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ConfigurationBuilder {

    public static Configuration build(Path path){
        Configuration configuration = new Configuration();
        ConfigurationOptions options = new ConfigurationOptions();
        if (configuration != null){
            ObjectMapper mapper = new ObjectMapper();

            try {
                if (path != null){
                    options = mapper.readValue(path.toFile(), ConfigurationOptions.class);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        configuration
            .setOptions(options)
            .setDefaultRules(getDefaultList())
            .setRegisteredBuilders(getDefaultBuilderList())
            .setConfiguredResultsWriters(getResultsWriters(options))
        ;

        return configuration;
    }

    protected static List<CoverageResultsWriter> getResultsWriters(ConfigurationOptions options){
        List<CoverageResultsWriter> configuredResultsWriters = new ArrayList<>();

        if (options.getWriters().isEmpty()) {
            configuredResultsWriters.add(
                    new HtmlBranchReportResultsWriter()
            );
        } else {
            options
                .getWriters()
                .entrySet()
                .forEach(
                    entry -> {
                        switch (entry.getKey().toLowerCase()){
                            case "html":
                                configuredResultsWriters.add(
                                    new HtmlBranchReportResultsWriter(entry.getValue().getLocale(),entry.getValue().getFilename())
                                );
                                break;
                        }
                    }
                );
        }

        return configuredResultsWriters;
    }

    protected static List<ConditionRule> getDefaultList(){
        List<ConditionRule>  registeredRules = new ArrayList<>();

        registeredRules.add(new OnlyDeclaretedHTTPStatusesRule());
        registeredRules.add(new HTTPStatusnRule());

        registeredRules.add(new DefaultParameterRule());
        registeredRules.add(new EmptyHeaderRule());
        registeredRules.add(new EnumValuesRule());
        registeredRules.add(new NotEmptyBodyRule());
        registeredRules.add(new NotOnlyEnumValuesRule());
        return registeredRules;
    }

    protected static List<StatisticsBuilder> getDefaultBuilderList(){
        List<StatisticsBuilder> registeredBuilders = new ArrayList<>();

        registeredBuilders.add(new CoverageStatisticsBuilder());
        registeredBuilders.add(new GenerationStatisticsBuilder());
        registeredBuilders.add(new BranchStatisticsBuilder());
        registeredBuilders.add(new ZeroCallStatisticsBuilder());
        registeredBuilders.add(new TagStatisticsBuilder());
        registeredBuilders.add(new ConfigurationStatisticsBuilder());
        registeredBuilders.add(new FlatOperationBuilder());

        return registeredBuilders;
    }
}
