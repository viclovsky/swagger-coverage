package ru.viclovsky.swagger.coverage;

import com.beust.jcommander.*;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.viclovsky.swagger.coverage.config.Configuration;
import ru.viclovsky.swagger.coverage.core.SwaggerCoverageExec;
import ru.viclovsky.swagger.coverage.core.filter.IgnoreParamsFilter;
import ru.viclovsky.swagger.coverage.core.filter.StatusOkFilter;
import ru.viclovsky.swagger.coverage.core.filter.SwaggerCoverageFilter;
import ru.viclovsky.swagger.coverage.option.MainOptions;
import ru.viclovsky.swagger.coverage.option.VerboseOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static io.swagger.models.ParamType.HEADER;

@Parameters(commandNames = "swagger-coverage", commandDescription = "Swagger-coverage Commandline")
public class CommandLine {

    @ParametersDelegate
    private MainOptions mainOptions = new MainOptions();

    @ParametersDelegate
    private VerboseOptions verboseOptions = new VerboseOptions();

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandLine.class);

    private final JCommander commander = new JCommander(this);

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

        List<SwaggerCoverageFilter> filters = new ArrayList<>();
        if (mainOptions.isIgnoreNotOkStatusCodes()) {
            filters.add(new StatusOkFilter());
        }

        if (!mainOptions.getIgnoreHeaders().isEmpty()) {
            filters.add(new IgnoreParamsFilter(mainOptions.getIgnoreHeaders(), HEADER));
        }

        Configuration configuration = Configuration.conf()
                .setSwaggerResults(mainOptions.isSwaggerOutput())
                .setOutputPath(mainOptions.getInputPath())
                .setSpecPath(mainOptions.getSpecPath());

        SwaggerCoverageExec.swaggerCoverage(configuration)
                .setFilters(filters)
                .execute();
        return ExitCode.NO_ERROR;
    }

    private void printUsage(final JCommander commander) {
        commander.usage();
    }
}
