package ru.viclovsky.swagger.coverage;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CommandLine {

    @Parameter
    private List<String> parameters = new ArrayList<>();

    @Parameter(names = "-spec", description = "Path to specification")
    private Path specPath;

    @Parameter(names = "-input", description = "Path to files with coverage")
    private Path inputPath;

    @Parameter(names = "-output", description = "Path to output")
    private Path outputPath;

    @Parameter(names = "-ignoreHeaders", description = "Ignore headers")
    private boolean ignoreHeaders;

    @Parameter(names = "-ignoreStatusCodes", description = "Ignore status codes")
    private boolean ignoreStatusCodes;

    public static void main(final String[] argv) {
        CommandLine commandLine = new CommandLine();
        JCommander.newBuilder()
                .addObject(commandLine)
                .build()
                .parse(argv);

        commandLine.run();
    }

    private void run() {
        Config config = Config.conf()
                .withInputPath(inputPath)
                .withSpecPath(specPath)
                .withOutputPath(outputPath)
                .withIgnoreHeaders(ignoreHeaders)
                .withIgnoreStatusCodes(ignoreStatusCodes);
        SwaggerCoverageExec.swaggerCoverage(config).execute();
    }
}
