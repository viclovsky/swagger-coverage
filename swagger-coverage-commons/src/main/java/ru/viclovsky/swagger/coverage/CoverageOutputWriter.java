package ru.viclovsky.swagger.coverage;

import io.swagger.models.Swagger;

public interface CoverageOutputWriter {
    void write(Swagger swagger);
}
