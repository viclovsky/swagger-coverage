package com.github.viclovsky.swagger.coverage.branch.util;

import com.beust.jcommander.Parameter;

import java.nio.file.Path;

public class MainUtilOptions {
    @Parameter(
            names = {"-s", "--spec"},
            description = "Path to swagger specification.",
            required = true,
            order = 0
    )
    private Path specPath;

    @Parameter(
            names = {"-i", "--input"},
            description = "Path to folder with generated files with coverage.",
            required = true,
            order = 1
    )
    private Path inputPath;

    public Path getSpecPath() {
        return specPath;
    }

    public MainUtilOptions setSpecPath(Path specPath) {
        this.specPath = specPath;
        return this;
    }

    public Path getInputPath() {
        return inputPath;
    }

    public MainUtilOptions setInputPath(Path inputPath) {
        this.inputPath = inputPath;
        return this;
    }
}
