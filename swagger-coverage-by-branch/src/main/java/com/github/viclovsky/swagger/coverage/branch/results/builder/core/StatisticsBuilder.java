package com.github.viclovsky.swagger.coverage.branch.results.builder.core;

import com.github.viclovsky.swagger.coverage.branch.results.Results;
import com.github.viclovsky.swagger.coverage.branch.rule.core.BranchRule;
import io.swagger.models.Swagger;

import java.util.List;

public abstract class StatisticsBuilder {

    public StatisticsBuilder(Swagger swagger, List<BranchRule> rules) {
    }

    public StatisticsBuilder add(String path){
        return this;
    }

    public StatisticsBuilder add(Swagger swagger) {
        return this;
    }

    public abstract void build(Results results);

    public abstract boolean isPreBuilder();
    public abstract boolean isPostBuilder();
}
