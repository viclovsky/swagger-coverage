package ru.viclovsky.swagger.coverage.config;

import java.nio.file.Path;

public class Configuration {

    private Path specPath;
    private Path inputPath;

    private boolean swaggerResults;
    private boolean simpleResults;

    private Configuration() {
    }

    public static Configuration conf() {
        return new Configuration();
    }

    public Configuration setSpecPath(Path specPath) {
        this.specPath = specPath;
        return this;
    }

    public Configuration setOutputPath(Path reqPath) {
        this.inputPath = reqPath;
        return this;
    }

    public Path getSpecPath() {
        return specPath;
    }

    public Path getOutputPath() {
        return inputPath;
    }

    public boolean isSwaggerResults() {
        return swaggerResults;
    }

    public Configuration setSwaggerResults(boolean swaggerResults) {
        this.swaggerResults = swaggerResults;
        return this;
    }

    public boolean isSimpleResults() {
        return simpleResults;
    }

    public Configuration setSimpleResults(boolean simpleResults) {
        this.simpleResults = simpleResults;
        return this;
    }
}
