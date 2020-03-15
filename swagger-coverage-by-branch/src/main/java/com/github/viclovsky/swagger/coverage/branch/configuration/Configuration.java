package com.github.viclovsky.swagger.coverage.branch.configuration;

import com.github.viclovsky.swagger.coverage.branch.configuration.options.ConfigurationOptions;
import com.github.viclovsky.swagger.coverage.branch.configuration.options.RuleConfigurationOptions;
import com.github.viclovsky.swagger.coverage.branch.results.builder.core.StatisticsBuilder;
import com.github.viclovsky.swagger.coverage.branch.rule.core.BranchRule;
import io.swagger.models.Swagger;

import java.util.List;
import java.util.stream.Collectors;

public class Configuration {
    protected ConfigurationOptions options = new ConfigurationOptions();
    protected List<BranchRule> defaultRules;
    protected List<StatisticsBuilder> registeredBuilders;

    /** Configured data **/
    protected List<BranchRule> configuredRules = null;
    protected List<StatisticsBuilder> configuredBuilders = null;


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
}
