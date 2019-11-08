package ru.viclovsky.swagger.coverage.config;

import java.nio.file.Path;

public class Config {

    private Path specPath;
    private Path inputPath;
    private boolean ignoreHeaders;
    private boolean ignoreStatusCodes;

    private Config() {
    }

    public static Config conf() {
        return new Config();
    }

    public Config withSpecPath(Path specPath) {
        this.specPath = specPath;
        return this;
    }

    public Config withInputPath(Path reqPath) {
        this.inputPath = reqPath;
        return this;
    }

    public Path getSpecPath() {
        return specPath;
    }

    public Path getInputPath() {
        return inputPath;
    }

    public Boolean getIgnoreHeaders() {
        return ignoreHeaders;
    }

    public Config withIgnoreHeaders(boolean ignoreHeaders) {
        this.ignoreHeaders = ignoreHeaders;
        return this;
    }

    public boolean getIgnoreStatusCodes() {
        return ignoreStatusCodes;
    }

    public Config withIgnoreStatusCodes(boolean ignoreStatusCodes) {
        this.ignoreStatusCodes = ignoreStatusCodes;
        return this;
    }
}
