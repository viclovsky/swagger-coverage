package com.github.viclovsky.swagger.coverage;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.ParametersDelegate;
import com.github.viclovsky.swagger.coverage.core.config.Configuration;
import com.github.viclovsky.swagger.coverage.core.generator.Generator;
import com.github.viclovsky.swagger.coverage.core.rule.ConditionRule;
import com.github.viclovsky.swagger.coverage.core.rule.DefaultOperationConditionRule;
import com.github.viclovsky.swagger.coverage.core.rule.parameter.DefaultBodyConditionRule;
import com.github.viclovsky.swagger.coverage.core.rule.parameter.DefaultEnumValuesConditionRule;
import com.github.viclovsky.swagger.coverage.core.rule.parameter.DefaultParameterConditionRule;
import com.github.viclovsky.swagger.coverage.core.rule.status.DefaultHTTPStatusConditionRule;
import com.github.viclovsky.swagger.coverage.core.writer.CoverageResultsWriter;
import com.github.viclovsky.swagger.coverage.core.writer.FileSystemResultsWriter;
import com.github.viclovsky.swagger.coverage.core.writer.HtmlReportResultsWriter;
import com.github.viclovsky.swagger.coverage.core.writer.LogResultsWriter;
import com.github.viclovsky.swagger.coverage.option.MainOptions;
import com.github.viclovsky.swagger.coverage.option.VerboseOptions;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.github.viclovsky.swagger.coverage.core.config.Configuration.conf;

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

        Configuration configuration = conf()
                .setInputPath(mainOptions.getInputPath())
                .setSpecPath(mainOptions.getSpecPath())
                .setRules(defaultRules())
                .setWriters(defaultWriters());
        Generator generator = new Generator()
                .setConfiguration(configuration);
        generator.run();

        return ExitCode.NO_ERROR;
    }

    static List<ConditionRule> defaultRules() {
        return Arrays.asList(
                new DefaultOperationConditionRule(),
                new DefaultParameterConditionRule(),
                new DefaultBodyConditionRule(),
                new DefaultHTTPStatusConditionRule(),
                new DefaultEnumValuesConditionRule());
    }

    static List<CoverageResultsWriter> defaultWriters() {
        return Arrays.asList(
                new LogResultsWriter(),
                new HtmlReportResultsWriter(),
                new FileSystemResultsWriter());
    }

    private void printUsage(final JCommander commander) {
        commander.usage();
    }
}
