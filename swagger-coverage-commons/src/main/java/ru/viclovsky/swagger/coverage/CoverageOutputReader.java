package ru.viclovsky.swagger.coverage;

import java.nio.file.Path;
import java.util.Set;

public interface CoverageOutputReader {

    Set<Path> getOutputs();
}
