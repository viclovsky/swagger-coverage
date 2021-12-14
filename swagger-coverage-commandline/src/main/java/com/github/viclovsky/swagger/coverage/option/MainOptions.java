package com.github.viclovsky.swagger.coverage.option;

import com.beust.jcommander.Parameter;

import java.net.URI;
import java.nio.file.Path;

public class MainOptions {

    @Parameter(
            names = {"-s", "--spec"},
            description = "Path to local or URL to remote swagger specification.",
            required = true,
            order = 0
    )
    private URI specPath;

    @Parameter(
            names = {"-i", "--input"},
            description = "Path to folder with generated files with coverage.",
            required = true,
            order = 1
    )
    private Path inputPath;

    @Parameter(
            names = {"-c", "--configuration"},
            description = "Path to file with report configuration.",
            order = 1
    )
    private Path configuration;

    @Parameter(
            names = "--help",
            description = "Print commandline help.",
            help = true,
            order = 5
    )
    private boolean help;

    public boolean isHelp() {
        return help;
    }

    public URI getSpecPath() {
        return specPath;
    }

    public Path getInputPath() {
        return inputPath;
    }

    public Path getConfiguration() {
        return configuration;
    }
}
