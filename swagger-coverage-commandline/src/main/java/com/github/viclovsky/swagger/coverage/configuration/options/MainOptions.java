package com.github.viclovsky.swagger.coverage.configuration.options;

public class MainOptions {
    protected boolean pathCaseIgnore = false;

    public boolean isPathCaseIgnore() {
        return pathCaseIgnore;
    }

    public MainOptions setPathCaseIgnore(boolean pathCaseIgnore) {
        this.pathCaseIgnore = pathCaseIgnore;
        return this;
    }

    @Override
    public String toString() {
        return "MainOptions{" +
                "pathCaseIgnore=" + pathCaseIgnore +
                '}';
    }
}
