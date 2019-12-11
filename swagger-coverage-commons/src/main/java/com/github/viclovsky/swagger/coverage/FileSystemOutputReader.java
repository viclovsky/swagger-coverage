package com.github.viclovsky.swagger.coverage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class FileSystemOutputReader implements CoverageOutputReader {

    private final Path outputDirectory;

    public FileSystemOutputReader(final Path outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    @Override
    public Set<Path> getOutputs() {
        Set<Path> outputs = new HashSet<>();
        try (Stream<Path> paths = Files.walk(outputDirectory)) {
            paths.filter(Files::isRegularFile).forEach(outputs::add);
        } catch (IOException e) {
            throw new SwaggerCoverageReadException("can't read coverage file's", e);
        }
        return outputs;
    }

}
