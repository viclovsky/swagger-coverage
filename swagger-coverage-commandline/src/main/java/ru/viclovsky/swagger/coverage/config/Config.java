package ru.viclovsky.swagger.coverage.config;

import java.nio.file.Path;

public class Config {

    private Path specPath;
    private Path inputPath;
    private String ignoreParams;
    private String ignoreStatusCodes;

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

    public String getIgnoreParams() {
        return ignoreParams;
    }

    public Config withIgnoreParams(String ignoreParams) {
        this.ignoreParams = ignoreParams;
        return this;
    }

    public String getIgnoreStatusCodes() {
        return ignoreStatusCodes;
    }

    public Config withIgnoreStatusCodes(String ignoreStatusCodes) {
        this.ignoreStatusCodes = ignoreStatusCodes;
        return this;
    }
}
