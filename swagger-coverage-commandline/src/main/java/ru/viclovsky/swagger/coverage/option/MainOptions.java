package ru.viclovsky.swagger.coverage.option;

import com.beust.jcommander.Parameter;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MainOptions {

    @Parameter(
            names = {"-s", "--spec"},
            description = "Path to swagger specification.",
            required = true,
            order = 0
    )
    private Path specPath;

    @Parameter(
            names = {"-i", "--input"},
            description = "Path to files with coverage.",
            required = true,
            order = 1
    )
    private Path inputPath;

    @Parameter(
            names = "--outputSwagger",
            description = "Return swagger.",
            order = 2
    )
    private boolean isSwaggerOutput;

    @Parameter(
            names = "--ignoreHeaders",
            description = "Ignore headers by name.",
            order = 3,
            variableArity = true
    )
    private List<String> ignoreHeaders;

    @Parameter(
            names = "--ignoreNotOkStatusCodes",
            description = "Ignore all status codes != 200.",
            order = 4
    )
    private boolean ignoreNotOkStatusCodes;

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

    public Path getSpecPath() {
        return specPath;
    }

    public Path getInputPath() {
        return inputPath;
    }

    public boolean isSwaggerOutput() {
        return isSwaggerOutput;
    }

    public List<String> getIgnoreHeaders() {
        return Optional.ofNullable(ignoreHeaders)
                .orElseGet(ArrayList::new);
    }

    public boolean isIgnoreNotOkStatusCodes() {
        return ignoreNotOkStatusCodes;
    }
}
