package com.github.viclovsky.swagger.coverage.branch.configuration;

import com.github.viclovsky.swagger.coverage.CoverageResultsWriter;
import com.github.viclovsky.swagger.coverage.branch.configuration.options.ConfigurationOptions;
import com.github.viclovsky.swagger.coverage.branch.configuration.options.RuleConfigurationOptions;
import com.github.viclovsky.swagger.coverage.branch.results.builder.core.StatisticsBuilder;
import com.github.viclovsky.swagger.coverage.branch.rule.core.BranchRule;
import com.github.viclovsky.swagger.coverage.branch.writer.HtmlBranchReportResultsWriter;
import io.swagger.models.Swagger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Configuration {
    protected ConfigurationOptions options = new ConfigurationOptions();
    protected List<BranchRule> defaultRules;
    protected List<StatisticsBuilder> registeredBuilders;

    /** Configured data **/
    protected List<BranchRule> configuredRules = null;
    protected List<StatisticsBuilder> configuredBuilders = null;

    protected List<CoverageResultsWriter> configuredResultsWriters = null;

    public Configuration() {
    }

    public List<BranchRule> getRulesList(){
        if (configuredRules == null) {
            configuredRules = defaultRules
                .stream()
                .filter(rule -> enableByRuleOptions(rule.getId()))
                .map(
                    rule -> rule.configure(
                        options
                            .getRules()
                            .getOrDefault(
                                rule.getId(), new RuleConfigurationOptions()
                            )
                    )
                )
                .collect(Collectors.toList())
            ;
        }

        return configuredRules;
    }

    public List<StatisticsBuilder> getStatisticsBuilders(Swagger specification){
        if (configuredBuilders == null){
            configuredBuilders = registeredBuilders
                .stream()
                .map(
                    builder -> builder
                        .configure(options)
                        .configure(specification,getRulesList())
                )
                .collect(Collectors.toList())
            ;
        }

        return configuredBuilders;
    }

    protected boolean enableByRuleOptions(String id){
        RuleConfigurationOptions option = options.getRules().get(id);
        if(option != null){
            return option.isEnable();
        }

        return true;
    }

    public Configuration setOptions(ConfigurationOptions options) {
        this.options = options;
        return this;
    }

    public Configuration setDefaultRules(List<BranchRule> defaultRules) {
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
