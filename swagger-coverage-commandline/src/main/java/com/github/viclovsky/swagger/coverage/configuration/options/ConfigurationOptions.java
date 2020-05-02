package com.github.viclovsky.swagger.coverage.configuration.options;

import java.util.HashMap;
import java.util.Map;

public class ConfigurationOptions {

    private Map<String, RuleConfigurationOptions> rules = new HashMap<>();
    private Map<String, ResultsWriterOptions> writers = new HashMap<>();
//    private MainOptions general = new MainOptions();

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

//    public MainOptions getGeneral() {
//        return general;
//    }
//
//    public ConfigurationOptions setGeneral(MainOptions general) {
//        this.general = general;
//        return this;
//    }

    @Override
    public String toString() {
        return "ConfigurationOptions{" +
                "rules=" + rules.toString() +
//                ", general=" + general.toString() +
                ", writers=" + writers.toString() +
                '}';
    }
}
