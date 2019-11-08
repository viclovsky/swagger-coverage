package ru.viclovsky.swagger.coverage.model;

import java.util.Set;

public class Problem {
    
    private int paramsCount;
    private int allParamsCount;
    private int statusCodesCount;
    private int allStatusCodesCount;

    private Set<String> params;
    private Set<String> statusCodes;

    public int getParamsCount() {
        return paramsCount;
    }

    public Problem withParamsCount(int paramsCount) {
        this.paramsCount = paramsCount;
        return this;
    }

    public int getAllParamsCount() {
        return allParamsCount;
    }

    public Problem withAllParamsCount(int allParamsCount) {
        this.allParamsCount = allParamsCount;
        return this;
    }

    public int getStatusCodesCount() {
        return statusCodesCount;
    }

    public Problem withStatusCodesCount(int statusCodesCount) {
        this.statusCodesCount = statusCodesCount;
        return this;
    }

    public int getAllStatusCodesCount() {
        return allStatusCodesCount;
    }

    public Problem withAllStatusCodesCount(int allStatusCodesCount) {
        this.allStatusCodesCount = allStatusCodesCount;
        return this;
    }

    public Set<String> getParams() {
        return params;
    }

    public Problem withParams(Set<String> params) {
        this.params = params;
        return this;
    }

    public Set<String> getStatusCodes() {
        return statusCodes;
    }

    public Problem withStatusCodes(Set<String> statusCodes) {
        this.statusCodes = statusCodes;
        return this;
    }

}
