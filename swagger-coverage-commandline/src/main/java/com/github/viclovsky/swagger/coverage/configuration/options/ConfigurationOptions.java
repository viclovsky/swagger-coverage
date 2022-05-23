package com.github.viclovsky.swagger.coverage.configuration.options;

import io.swagger.v3.parser.core.models.ParseOptions;

import java.util.HashMap;
import java.util.Map;

public class ConfigurationOptions {

    private Map<String, RuleConfigurationOptions> rules = new HashMap<>();
    private Map<String, ResultsWriterOptions> writers = new HashMap<>();

    public Map<String, RuleConfigurationOptions> getRules() {
        return rules;
    }

    public ConfigurationOptions setRules(Map<String, RuleConfigurationOptions> rules) {
        this.rules = rules;
        return this;
    }

    public Map<String, ResultsWriterOptions> getWriters() {
        return writers;
    }

    public ConfigurationOptions setWriters(Map<String, ResultsWriterOptions> writers) {
        this.writers = writers;
        return this;
    }

    @Override
    public String toString() {
        return "ConfigurationOptions{" +
                "rules=" + rules.toString() +
                ", writers=" + writers.toString() +
                '}';
    }
}
