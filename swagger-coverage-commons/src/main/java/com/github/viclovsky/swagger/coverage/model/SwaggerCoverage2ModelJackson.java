package com.github.viclovsky.swagger.coverage.model;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.databind.MapperFeature.USE_WRAPPER_NAME_AS_PROPERTY_NAME;

@SuppressWarnings("PMD.ClassNamingConventions")
public final class SwaggerCoverage2ModelJackson {

    private SwaggerCoverage2ModelJackson() {
        throw new IllegalStateException("Do not instance SwaggerCoverage2ModelJackson");
    }

    public static ObjectMapper createJsonMapper() {
        return new ObjectMapper()
                .configure(USE_WRAPPER_NAME_AS_PROPERTY_NAME, true)
                .setSerializationInclusion(NON_NULL);
    }

    public static ObjectMapper createYamlMapper() {
        return new ObjectMapper(new YAMLFactory())
                .configure(USE_WRAPPER_NAME_AS_PROPERTY_NAME, true)
                .setSerializationInclusion(NON_NULL);
    }

}