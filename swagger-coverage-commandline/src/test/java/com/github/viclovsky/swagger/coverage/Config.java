package com.github.viclovsky.swagger.coverage;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;

import static java.util.Optional.ofNullable;

public class Config {
    private final String path;
    private final String outputPath;
    private final String specPath;

    public Config(String path, String outputPath, String specPath) {
        this.path = path;
        this.outputPath = outputPath;
        this.specPath = specPath;
    }

    public Path getPath() {
        return getFile(path).toPath();
    }

    public Path getOutput() {
        return getFile(outputPath).toPath();
    }

    public URI getSpec() {
        return URI.create(specPath);
    }

    private File getFile(String name) {
        return ofNullable(getClass().getClassLoader().getResource(name))
                .map(spec -> new File(spec.getFile()))
                .orElseThrow(() -> new IllegalArgumentException("Unable to read file: " + name));
    }
}
