package com.github.viclovsky.swagger.coverage.core.rule.core;

import com.github.viclovsky.swagger.coverage.configuration.options.RuleConfigurationOptions;
import com.github.viclovsky.swagger.coverage.core.model.Condition;
import io.swagger.v3.oas.models.Operation;

import java.util.List;

public abstract class ConditionRule {
    protected RuleConfigurationOptions options;

    public abstract String getId();

    public abstract List<Condition> createCondition(Operation operation);

    public ConditionRule configure(RuleConfigurationOptions options) {
        this.options = options;
        return this;
    }

    protected final boolean includesEnabled() {
        return options != null && options.getFilter() != null && !options.getFilter().isEmpty();
    }

    protected final boolean excludesEnabled() {
        return options != null && options.getIgnore() != null && !options.getIgnore().isEmpty();
    }

    protected final boolean isIncluded(String val) {
        return includesEnabled() && options.getFilter().contains(val);
    }

    protected final boolean isExcluded(String val) {
        return excludesEnabled() && options.getIgnore().contains(val);
    }
}
