package com.github.viclovsky.swagger.coverage.branch.predicate;

import io.swagger.models.parameters.Parameter;

import java.util.function.Predicate;

class ParameterUtils {

    private ParameterUtils() {}

    static Predicate<Parameter> equalsParam(String name, String in) {
        return p -> (p.getName().equals(name) && p.getIn().equals(in));
    }
}
