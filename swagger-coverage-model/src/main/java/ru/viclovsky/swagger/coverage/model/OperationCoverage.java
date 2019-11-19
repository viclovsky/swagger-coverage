package ru.viclovsky.swagger.coverage.model;

import java.util.Set;

public class OperationCoverage {
    
    private Set<String> params;
    private Set<String> statusCodes;

    public Set<String> getParams() {
        return params;
    }

    public OperationCoverage setParams(Set<String> params) {
        this.params = params;
        return this;
    }

    public Set<String> getStatusCodes() {
        return statusCodes;
    }

    public OperationCoverage setStatusCodes(Set<String> statusCodes) {
        this.statusCodes = statusCodes;
        return this;
    }
}
