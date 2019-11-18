package ru.viclovsky.swagger.coverage;

import io.swagger.models.Swagger;

public interface CoverageResultsWriter {
    void write(Swagger swagger);
}
