package com.github.viclovsky.swagger.coverage.branch.rule.core;

import com.github.viclovsky.swagger.coverage.branch.configuration.options.RuleConfigurationOptions;
import com.github.viclovsky.swagger.coverage.branch.model.Branch;
import io.swagger.models.Operation;

import java.util.List;

public abstract class BranchRule {
    public abstract String getId();
    public abstract List<Branch> createBranch(Operation operation);

    public BranchRule configure(RuleConfigurationOptions options){
        return this;
    };
}
