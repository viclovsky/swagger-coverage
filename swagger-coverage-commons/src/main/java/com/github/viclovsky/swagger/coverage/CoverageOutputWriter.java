package com.github.viclovsky.swagger.coverage;

import io.swagger.models.Swagger;
import io.swagger.v3.oas.models.OpenAPI;

public interface CoverageOutputWriter {

    void write(Swagger swagger);

    void write(OpenAPI openAPI);

}
