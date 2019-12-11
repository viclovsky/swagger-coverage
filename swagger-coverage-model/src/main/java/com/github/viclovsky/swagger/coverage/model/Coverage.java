package com.github.viclovsky.swagger.coverage.model;

import java.util.Set;
import java.util.TreeSet;

public class Coverage {

    private Set<String> params = new TreeSet<>();
    private Set<String> statusCodes = new TreeSet<>();

    private Set<String> coveredParams = new TreeSet<>();
    private Set<String> coveredStatusCodes = new TreeSet<>();

    private Set<String> ignoredParams = new TreeSet<>();
    private Set<String> ignoredStatusCodes = new TreeSet<>();

    public Set<String> getParams() {
        return params;
    }

    public Coverage setParams(Set<String> params) {
        this.params = params;
        return this;
    }

    public Set<String> getStatusCodes() {
        return statusCodes;
    }

    public Coverage setStatusCodes(Set<String> statusCodes) {
        this.statusCodes = statusCodes;
        return this;
    }

    public Set<String> getCoveredParams() {
        return coveredParams;
    }

    public Coverage setCoveredParams(Set<String> params) {
        this.coveredParams = params;
        return this;
    }

    public Set<String> getCoveredStatusCodes() {
        return coveredStatusCodes;
    }

    public Coverage setCoveredStatusCodes(Set<String> statusCodes) {
        this.coveredStatusCodes = statusCodes;
        return this;
    }


    public Set<String> getIgnoredParams() {
        return ignoredParams;
    }

    public Coverage setIgnoredParams(Set<String> params) {
        this.ignoredParams = params;
        return this;
    }

    public Set<String> getIgnoredStatusCodes() {
        return ignoredStatusCodes;
    }

    public Coverage setIgnoredStatusCodes(Set<String> statusCodes) {
        this.ignoredStatusCodes = statusCodes;
        return this;
    }

}
