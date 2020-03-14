package com.github.viclovsky.swagger.coverage.branch.results.builder.core;

import com.github.viclovsky.swagger.coverage.branch.results.builder.core.StatisticsBuilder;
import com.github.viclovsky.swagger.coverage.branch.rule.core.BranchRule;
import io.swagger.models.Swagger;

import java.util.List;

public abstract class StatisticsPostBuilder extends StatisticsBuilder {

    public StatisticsPostBuilder(Swagger swagger, List<BranchRule> rules) {
        super(swagger, rules);
    }

    @Override
    public boolean isPreBuilder() {
        return false;
    }

    @Override
    public boolean isPostBuilder() {
        return true;
    }
}
