package com.github.viclovsky.swagger.coverage.configuration.options;

import java.util.List;

public class RuleConfigurationOptions {
    protected boolean enable = true;
    protected List<String> filter;
    protected List<String> ignore;

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

    public List<String> getIgnore() {
        return ignore;
    }

    public RuleConfigurationOptions setIgnore(List<String> ignore) {
        this.ignore = ignore;
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
