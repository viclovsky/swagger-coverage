package com.github.viclovsky.swagger.coverage.core.rule.core;

import com.github.viclovsky.swagger.coverage.configuration.options.RuleConfigurationOptions;
import com.github.viclovsky.swagger.coverage.core.model.Condition;
import io.swagger.models.Operation;

import java.util.List;

public abstract class ConditionRule {
    protected RuleConfigurationOptions options;

    public abstract String getId();
    public abstract List<Condition> createCondition(Operation operation);

    public ConditionRule configure(RuleConfigurationOptions options){
        this.options = options;
        return this;
    };
}
