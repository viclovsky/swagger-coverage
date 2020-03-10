package com.github.viclovsky.swagger.coverage.branch.util;

import com.beust.jcommander.*;
import com.github.viclovsky.swagger.coverage.branch.generator.Generator;

import java.nio.file.Paths;


@Parameters(commandNames = "swagger-coverage", commandDescription = "Swagger-coverage Commandline")
public class CommandLineUtil {

    @ParametersDelegate
    private MainUtilOptions mainOptions = new MainUtilOptions();

    private final JCommander commander = new JCommander(this);

    public static void main(final String[] argv) {
        final CommandLineUtil commandLine = new CommandLineUtil();

        if (argv.length == 0) {
            System.exit(127);
        }

        commandLine.run(argv);
    }

    private void run(final String[] argv) {
        commander.parse(argv);

        Generator generator = new Generator();
        generator
                .setInputPath(mainOptions.getInputPath())
                .setSpecPath(mainOptions.getSpecPath())
                .run();

        System.exit(0);
    }
}
