package com.github.viclovsky.swagger.coverage.branch.configuration.options;

import java.util.List;

public class RuleConfigurationOptions {
    protected boolean enable = true;
    protected List<String> filter;

    public boolean isEnable() {
        return enable;
    }

    public RuleConfigurationOptions setEnable(boolean enable) {
        this.enable = enable;
        return this;
    }

    public List<String> getFilter() {
        return filter;
    }

    public RuleConfigurationOptions setFilter(List<String> filter) {
        this.filter = filter;
        return this;
    }

    @Override
    public String toString() {
        return "RuleConfigurationOptions{" +
                "enable=" + enable +
                ", filter=" + filter +
                '}';
    }
}
