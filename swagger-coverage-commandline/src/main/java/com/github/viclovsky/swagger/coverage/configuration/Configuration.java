package com.github.viclovsky.swagger.coverage.configuration;

import com.github.viclovsky.swagger.coverage.configuration.options.ConfigurationOptions;
import com.github.viclovsky.swagger.coverage.configuration.options.RuleConfigurationOptions;
import com.github.viclovsky.swagger.coverage.core.results.builder.core.StatisticsBuilder;
import com.github.viclovsky.swagger.coverage.core.rule.core.ConditionRule;
import com.github.viclovsky.swagger.coverage.core.writer.CoverageResultsWriter;
import io.swagger.models.Swagger;

import java.util.List;
import java.util.stream.Collectors;

public class Configuration {
    protected ConfigurationOptions options = new ConfigurationOptions();
    private List<ConditionRule> defaultRules;
    private List<StatisticsBuilder> registeredBuilders;

    /**
     * Configured data
     **/
    private List<ConditionRule> configuredRules = null;
    private List<StatisticsBuilder> configuredBuilders = null;
    private List<CoverageResultsWriter> configuredResultsWriters = null;

    public Configuration() {
    }

    public List<ConditionRule> getRulesList() {
        if (configuredRules == null) {
            configuredRules = defaultRules.stream()
                    .filter(rule -> enableByRuleOptions(rule.getId()))
                    .map(rule -> rule.configure(options.getRules().getOrDefault(rule.getId(),
                            new RuleConfigurationOptions())))
                    .collect(Collectors.toList());
        }
        return configuredRules;
    }

    public List<StatisticsBuilder> getStatisticsBuilders(Swagger specification) {
        if (configuredBuilders == null) {
            configuredBuilders = registeredBuilders
                    .stream().map(builder -> builder
                            .configure(options)
                            .configure(specification, getRulesList())
                    )
                    .collect(Collectors.toList());
        }

        return configuredBuilders;
    }

    protected boolean enableByRuleOptions(String id) {
        RuleConfigurationOptions option = options.getRules().get(id);
        if (option != null) {
            return option.isEnable();
        }

        return true;
    }

    public Configuration setOptions(ConfigurationOptions options) {
        this.options = options;
        return this;
    }

    public RuleConfigurationOptions getOption(String optionKey) {
        return this.options.getRules().get(optionKey);
    }

    public Configuration setDefaultRules(List<ConditionRule> defaultRules) {
        this.defaultRules = defaultRules;
        return this;
    }

    public Configuration setRegisteredBuilders(List<StatisticsBuilder> registeredBuilders) {
        this.registeredBuilders = registeredBuilders;
        return this;
    }

    public List<StatisticsBuilder> getConfiguredBuilders() {
        return configuredBuilders;
    }

    public Configuration setConfiguredBuilders(List<StatisticsBuilder> configuredBuilders) {
        this.configuredBuilders = configuredBuilders;
        return this;
    }

    public List<CoverageResultsWriter> getConfiguredResultsWriters() {
        return configuredResultsWriters;
    }

    public Configuration setConfiguredResultsWriters(List<CoverageResultsWriter> configuredResultsWriters) {
        this.configuredResultsWriters = configuredResultsWriters;
        return this;
    }
}
