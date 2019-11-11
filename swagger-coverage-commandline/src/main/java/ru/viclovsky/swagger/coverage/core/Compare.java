package ru.viclovsky.swagger.coverage.core;

import io.swagger.models.Operation;
import io.swagger.models.Swagger;
import io.swagger.models.parameters.Parameter;
import ru.viclovsky.swagger.coverage.model.OperationCoverage;
import ru.viclovsky.swagger.coverage.model.Coverage;

import java.util.*;
import java.util.function.Predicate;

import static ru.viclovsky.swagger.coverage.model.OperationCoverage.operationCoverage;

final class Compare {

    private static final String BODY_PARAM_NAME = "body";
    private Map<String, Operation> emptyCoverage;
    private Map<String, OperationCoverage> partialCoverage;
    private Map<String, OperationCoverage> fullCoverage;
    private Map<String, Operation> spec;

    public Compare(Swagger spec, Swagger temp) {
        this.spec = getOperationMap(spec);
        this.emptyCoverage = getOperationMap(temp);
        this.partialCoverage = new HashMap<>();
        this.fullCoverage = new HashMap<>();
    }

    public Compare addCoverage(Collection<Swagger> coverageSpecs) {
        coverageSpecs.forEach(this::addCoverage);
        return this;
    }

    public Compare addCoverage(Swagger coverageSpec) {
        Map<String, Operation> coverage = getOperationMap(coverageSpec);
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
                op = processOperation(operation, partialCoverage.get(k).getModified());
                partialCoverage.remove(k);
            }

            if (isEmptyOperation(Objects.requireNonNull(op))) {
                fullCoverage.put(k, operationCoverage().withModified(op).withOriginal(spec.get(k)));
                return;
            }
            partialCoverage.put(k, operationCoverage().withModified(op).withOriginal(spec.get(k)));
        });
        return this;
    }

    /**
     * HTTP method + path = Operation
     */
    private Map<String, Operation> getOperationMap(Swagger swagger) {
        Map<String, Operation> coverage = new HashMap<>();
        swagger.getPaths().keySet().forEach(path
                -> swagger.getPaths().get(path).getOperationMap().forEach((httpMethod, operation)
                -> coverage.put(String.format("%s %s", httpMethod, path), operation)
        ));
        return coverage;
    }

    private Boolean isEmptyOperation(Operation operation) {
        return (operation.getParameters().isEmpty() && operation.getResponses().isEmpty());
    }

    private Operation processOperation(Operation current, Operation expected) {
        if (Objects.nonNull(current.getResponses())) {
            current.getResponses().forEach((status, resp) ->
                    expected.getResponses().remove(status));
        }

        if (Objects.nonNull(current.getParameters())) {
            current.getParameters().forEach(parameter ->
                    expected.getParameters().stream()
                            .filter(equalsParam(parameter).or(equalsBody())).findFirst()
                            .ifPresent(presentParam -> expected.getParameters().remove(presentParam)));
        }
        return expected;
    }

    public Coverage getCoverage() {
        return Coverage.coverage()
                .withEmpty(emptyCoverage)
                .withFull(fullCoverage)
                .withPartial(partialCoverage);
    }

    private static Predicate<Parameter> equalsParam(Parameter parameter) {
        return p -> (p.getName().equals(parameter.getName()) && p.getIn().equals(parameter.getIn()));
    }

    private static Predicate<Parameter> equalsBody() {
        return p -> BODY_PARAM_NAME.equalsIgnoreCase(p.getIn());
    }

}
