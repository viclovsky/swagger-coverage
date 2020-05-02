package com.github.viclovsky.swagger.coverage;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.ParametersDelegate;
import com.github.viclovsky.swagger.coverage.core.generator.Generator;
import com.github.viclovsky.swagger.coverage.option.MainOptions;
import com.github.viclovsky.swagger.coverage.option.VerboseOptions;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;


@Parameters(commandNames = "swagger-coverage", commandDescription = "Swagger-coverage Commandline")
public class CommandLine {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandLine.class);
    private final JCommander commander = new JCommander(this);

    @ParametersDelegate
    private MainOptions mainOptions = new MainOptions();

    @ParametersDelegate
    private VerboseOptions verboseOptions = new VerboseOptions();

    public static void main(final String[] argv) {
        final CommandLine commandLine = new CommandLine();

        final ExitCode exitCode = commandLine
                .parse(argv)
                .orElseGet(commandLine::run);
        System.exit(exitCode.getCode());
    }

    @SuppressWarnings({"PMD.AvoidLiteralsInIfCondition", "ReturnCount"})
    public Optional<ExitCode> parse(final String... args) {
        if (args.length == 0) {
            printUsage(commander);
            return Optional.of(ExitCode.ARGUMENT_PARSING_ERROR);
        }
        try {
            commander.parse(args);
        } catch (ParameterException e) {
            LOGGER.debug("Error during arguments parsing: {}", e);
            LOGGER.info("Could not parse arguments: {}", e.getMessage());
            printUsage(commander);
            return Optional.of(ExitCode.ARGUMENT_PARSING_ERROR);
        }

        return Optional.empty();
    }

    private ExitCode run() {
        if (verboseOptions.isQuiet()) {
            LogManager.getRootLogger().setLevel(Level.OFF);
        }

        if (verboseOptions.isVerbose()) {
            LogManager.getRootLogger().setLevel(Level.DEBUG);
        }

        if (mainOptions.isHelp()) {
            printUsage(commander);
            return ExitCode.NO_ERROR;
        }

        new Generator().setInputPath(mainOptions.getInputPath())
                .setSpecPath(mainOptions.getSpecPath())
                .setConfigurationPath(mainOptions.getConfiguration())
                .run();

        return ExitCode.NO_ERROR;
    }


    private void printUsage(final JCommander commander) {
        commander.usage();
    }
}
