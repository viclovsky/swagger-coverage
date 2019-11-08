package ru.viclovsky.swagger.coverage.option;

import com.beust.jcommander.Parameter;

public class FilterOptions {

    @Parameter(names = "--ignoreHeaders",
            description = "Ignore all headers.")
    private boolean ignoreHeaders;

    @Parameter(names = "--ignoreStatusCodes",
            description = "Ignore status codes, except status code 200.")
    private boolean ignoreStatusCodes;

    public boolean isIgnoreHeaders() {
        return ignoreHeaders;
    }

    public boolean isIgnoreStatusCodes() {
        return ignoreStatusCodes;
    }
}
