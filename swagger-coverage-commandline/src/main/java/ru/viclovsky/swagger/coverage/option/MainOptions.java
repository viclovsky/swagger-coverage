package ru.viclovsky.swagger.coverage.option;

import com.beust.jcommander.Parameter;

import java.nio.file.Path;

public class MainOptions {

    @Parameter(
            names = "--spec",
            description = "Path to swagger specification.",
            required = true,
            order = 0
    )
    private Path specPath;

    @Parameter(
            names = "--input",
            description = "Path to files with coverage.",
            required = true,
            order = 1
    )
    private Path inputPath;

    @Parameter(
            names = "--ignoreHeaders",
            description = "Ignore headers by regexp.",
            order = 2
    )
    private String ignoreHeaders;

    @Parameter(
            names = "--ignoreStatusCodes",
            description = "Ignore status codes by regexp",
            order = 3
    )
    private String ignoreStatusCodes;

    @Parameter(
            names = "--help",
            description = "Print commandline help.",
            help = true,
            order = 4
    )
    private boolean help;

    public boolean isHelp() {
        return help;
    }

    public Path getSpecPath() {
        return specPath;
    }

    public Path getInputPath() {
        return inputPath;
    }

    public String getIgnoreHeaders() {
        return ignoreHeaders;
    }

    public String getIgnoreStatusCodes() {
        return ignoreStatusCodes;
    }
}
