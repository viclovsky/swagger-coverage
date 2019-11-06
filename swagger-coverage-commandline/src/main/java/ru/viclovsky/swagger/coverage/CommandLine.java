package ru.viclovsky.swagger.coverage;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CommandLine {

    @Parameter
    private List<String> parameters = new ArrayList<>();

    @Parameter(names = "-spec", description = "Path to specification", required = true)
    private Path specPath;

    @Parameter(names = "-input", description = "Path to files with coverage", required = true)
    private Path inputPath;

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

    public void run() {
        Config config = Config.conf()
                .withInputPath(inputPath)
                .withSpecPath(specPath)
                .withIgnoreHeaders(ignoreHeaders)
                .withIgnoreStatusCodes(ignoreStatusCodes);
        SwaggerCoverageExec.swaggerCoverage(config).execute();
    }
}
