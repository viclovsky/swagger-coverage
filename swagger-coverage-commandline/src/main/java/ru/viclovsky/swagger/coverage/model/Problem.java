package ru.viclovsky.swagger.coverage.model;

import java.util.Set;

public class Problem {
    
    private int paramsCount;
    private int ignoredParamsCount;
    private int allParamsCount;
    private int statusCodesCount;
    private int ignoredStatusCodesCount;
    private int allStatusCodesCount;

    private Set<String> params;
    private Set<String> statusCodes;
    private Set<String> ignoredParams;
    private Set<String> ignoredStatusCodes;

    public int getParamsCount() {
        return paramsCount;
    }

    public Problem setParamsCount(int paramsCount) {
        this.paramsCount = paramsCount;
        return this;
    }

    public int getAllParamsCount() {
        return allParamsCount;
    }

    public Problem setAllParamsCount(int allParamsCount) {
        this.allParamsCount = allParamsCount;
        return this;
    }

    public int getStatusCodesCount() {
        return statusCodesCount;
    }

    public Problem setStatusCodesCount(int statusCodesCount) {
        this.statusCodesCount = statusCodesCount;
        return this;
    }

    public int getAllStatusCodesCount() {
        return allStatusCodesCount;
    }

    public Problem setAllStatusCodesCount(int allStatusCodesCount) {
        this.allStatusCodesCount = allStatusCodesCount;
        return this;
    }

    public Set<String> getParams() {
        return params;
    }

    public Problem setParams(Set<String> params) {
        this.params = params;
        return this;
    }

    public Set<String> getStatusCodes() {
        return statusCodes;
    }

    public Problem setStatusCodes(Set<String> statusCodes) {
        this.statusCodes = statusCodes;
        return this;
    }

    public int getIgnoredParamsCount() {
        return ignoredParamsCount;
    }

    public Problem setIgnoredParamsCount(int ignoredParamsCount) {
        this.ignoredParamsCount = ignoredParamsCount;
        return this;
    }

    public int getIgnoredStatusCodesCount() {
        return ignoredStatusCodesCount;
    }

    public Problem setIgnoredStatusCodesCount(int ignoredStatusCodesCount) {
        this.ignoredStatusCodesCount = ignoredStatusCodesCount;
        return this;
    }

    public Set<String> getIgnoredParams() {
        return ignoredParams;
    }

    public Problem setIgnoredParams(Set<String> ignoredParams) {
        this.ignoredParams = ignoredParams;
        return this;
    }

    public Set<String> getIgnoredStatusCodes() {
        return ignoredStatusCodes;
    }

    public Problem setIgnoredStatusCodes(Set<String> ignoredStatusCodes) {
        this.ignoredStatusCodes = ignoredStatusCodes;
        return this;
    }

}
