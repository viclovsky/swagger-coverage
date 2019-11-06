package ru.viclovsky.swagger.coverage;

import java.nio.file.Path;

public class Config {

    private Path specPath;
    private Path reqPath;
    private Path outputPath;
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

    public Config withReqPath(Path reqPath) {
        this.reqPath = reqPath;
        return this;
    }

    public Config withOutputPath(Path outputPath) {
        this.outputPath = outputPath;
        return this;
    }

    public Path getSpecPath() {
        return specPath;
    }

    public Path getReqPath() {
        return reqPath;
    }

    public Path getOutputPath() {
        return outputPath;
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
