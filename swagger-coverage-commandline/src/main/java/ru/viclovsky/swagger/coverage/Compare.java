package ru.viclovsky.swagger.coverage;

import io.swagger.models.Operation;
import io.swagger.models.Swagger;
import io.swagger.models.parameters.Parameter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

final class Compare {

    private static final String BODY_PARAM_NAME = "body";
    private Map<String, Operation> emptyCoverage;
    private Map<String, Operation> partialCoverage;
    private Map<String, Operation> fullCoverage;
    private Swagger spec;


    public Compare(Swagger spec) {
        this.spec = spec;
        this.emptyCoverage = getCoverage(spec);
        this.partialCoverage = new HashMap<>();
        this.fullCoverage = new HashMap<>();
    }

    /**
     * HTTP method + path = Operation
     */
    private Map<String, Operation> getCoverage(Swagger swagger) {
        Map<String, Operation> coverage = new HashMap<>();
        swagger.getPaths().keySet().forEach(path
                -> swagger.getPaths().get(path).getOperationMap().forEach((httpMethod, operation)
                -> coverage.put(String.format("%s %s", httpMethod, path), operation)
        ));
        return coverage;
    }

    public Compare addCoverage(Swagger spec) {
        Map<String, Operation> coverage = getCoverage(spec);
        coverage.forEach((k, operation) -> {
            if (fullCoverage.containsKey(k)) {
                return;
            }
            Operation op = null;
            if (emptyCoverage.containsKey(k)) {
                op = processOperation(operation, emptyCoverage.get(k));
                emptyCoverage.remove(k);
            }

            if (partialCoverage.containsKey(k)) {
                op = processOperation(operation, partialCoverage.get(k));
                partialCoverage.remove(k);
            }

            if (isEmptyOperation(Objects.requireNonNull(op))) {
                fullCoverage.put(k, op);
            } else {
                partialCoverage.put(k, op);
            }
        });
        return this;
    }

    private Boolean isEmptyOperation(Operation operation) {
        return (operation.getParameters().isEmpty() && operation.getResponses().isEmpty());
    }

    public Operation processOperation(Operation current, Operation expected) {
        //process response
        if (Objects.nonNull(current.getResponses())) {
            current.getResponses().forEach((status, resp) -> expected.getResponses().remove(status));
        }
        //process parameters
        if (Objects.nonNull(current.getParameters())) {
            current.getParameters().forEach(parameter ->
                    expected.getParameters().stream()
                            .filter(equalsParam(parameter).or(equalsBody())).findFirst()
                            .ifPresent(presentParam -> expected.getParameters().remove(presentParam)));
        }
        return expected;
    }

    public Output getOutput() {
        return Output.output()
                .withEmptyCoverage(emptyCoverage)
                .withFullCoverage(fullCoverage)
                .withPartialCoverage(partialCoverage);
    }

    private static Predicate<Parameter> equalsParam(Parameter parameter) {
        return p -> (p.getName().equals(parameter.getName()) && p.getIn().equals(parameter.getIn()));
    }

    private static Predicate<Parameter> equalsBody() {
        return p -> BODY_PARAM_NAME.equalsIgnoreCase(p.getIn());
    }

}
