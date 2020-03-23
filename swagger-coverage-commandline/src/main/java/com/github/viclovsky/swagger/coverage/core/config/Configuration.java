package com.github.viclovsky.swagger.coverage.core.config;

import com.github.viclovsky.swagger.coverage.core.rule.ConditionRule;
import com.github.viclovsky.swagger.coverage.core.writer.CoverageResultsWriter;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Configuration {

    private Path specPath;
    private Path inputPath;
    private List<ConditionRule> rules = new ArrayList<>();
    private List<CoverageResultsWriter> writers = new ArrayList<>();

    private Configuration() {
    }

    public static Configuration conf() {
        return new Configuration();
    }

    public Configuration setSpecPath(Path specPath) {
        this.specPath = specPath;
        return this;
    }

    public Configuration setInputPath(Path reqPath) {
        this.inputPath = reqPath;
        return this;
    }

    public Path getSpecPath() {
        return specPath;
    }

    public Path getInputPath() {
        return inputPath;
    }


    public List<ConditionRule> getRules() {
        return rules;
    }

    public Configuration setRules(List<ConditionRule> rules) {
        this.rules = rules;
        return this;
    }

    public List<CoverageResultsWriter> getWriters() {
        return writers;
    }

    public Configuration setWriters(List<CoverageResultsWriter> writers) {
        this.writers = writers;
        return this;
    }
}
