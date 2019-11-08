package ru.viclovsky.swagger.coverage.option;

import com.beust.jcommander.Parameter;

import java.nio.file.Path;

public class MainOptions {

    @Parameter(
            names = "--help",
            description = "Print commandline help.",
            help = true
    )
    private boolean help;

    @Parameter(names = "--spec",
            description = "Path to swagger specification.",
            required = true)
    private Path specPath;

    @Parameter(names = "--input",
            description = "Path to files with coverage.",
            required = true)
    private Path inputPath;

    public boolean isHelp() {
        return help;
    }

    public Path getSpecPath() {
        return specPath;
    }

    public Path getInputPath() {
        return inputPath;
    }
}
