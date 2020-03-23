package com.github.viclovsky.swagger.coverage.branch.results.builder.core;

import com.github.viclovsky.swagger.coverage.branch.configuration.options.ConfigurationOptions;
import com.github.viclovsky.swagger.coverage.branch.configuration.options.MainOptions;
import com.github.viclovsky.swagger.coverage.branch.results.Results;
import com.github.viclovsky.swagger.coverage.branch.rule.core.BranchRule;
import io.swagger.models.Swagger;

import java.util.List;

public abstract class StatisticsBuilder {

    protected ConfigurationOptions options;

    public StatisticsBuilder add(String path){
        return this;
    }

    public StatisticsBuilder add(Swagger swagger) {
        return this;
    }

    public StatisticsBuilder configure(ConfigurationOptions options){
        this.options = options;
        return this;
    };

    public abstract StatisticsBuilder configure(Swagger swagger, List<BranchRule> rules);
    public abstract void build(Results results);

    public abstract boolean isPreBuilder();
    public abstract boolean isPostBuilder();
}
