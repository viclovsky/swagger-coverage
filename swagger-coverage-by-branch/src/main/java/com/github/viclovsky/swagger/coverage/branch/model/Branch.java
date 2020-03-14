package com.github.viclovsky.swagger.coverage.branch.model;

import com.github.viclovsky.swagger.coverage.branch.predicate.BranchPredicate;
import io.swagger.models.Response;
import io.swagger.models.parameters.Parameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Branch {

    protected String name;
    protected String description;
    protected boolean covered = false;

    public Branch(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public abstract void postCheck();

    public abstract boolean isHasPostCheck();

    public abstract boolean isNeedCheck();

    public abstract boolean check(Map<String, Parameter> params, Map<String, Response> responses);

    public abstract String getReason();

    public abstract String getType();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCovered() {
        return covered;
    }

    public void setCovered(boolean covered) {
        this.covered = covered;
    }

    @Override
    public String toString() {
        return "Branch{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", covered=" + covered +
                '}';
    }
}
