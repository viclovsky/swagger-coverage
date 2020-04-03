package com.github.viclovsky.swagger.coverage.core.results.builder.core;

public abstract class StatisticsPostBuilder extends StatisticsBuilder {

    public StatisticsPostBuilder() {
        super();
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
