package com.github.viclovsky.swagger.coverage.option;

import com.beust.jcommander.Parameter;

public class VerboseOptions {

    @Parameter(
            names = {"-v", "--verbose"},
            description = "Switch on the verbose mode."
    )
    private boolean verbose;

    @Parameter(
            names = {"-q", "--quiet"},
            description = "Switch on the quiet mode."
    )
    private boolean quiet;

    /**
     * Returns true if silent mode is enabled, false otherwise.
     */
    public boolean isQuiet() {
        return quiet;
    }

    /**
     * Returns true if verbose mode is enabled, false otherwise.
     */
    public boolean isVerbose() {
        return verbose;
    }
}
