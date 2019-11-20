package ru.viclovsky.swagger.coverage.core;

import io.swagger.models.Operation;
import io.swagger.models.Swagger;
import org.apache.log4j.Logger;
import ru.viclovsky.swagger.coverage.core.filter.SwaggerCoverageFilter;
import ru.viclovsky.swagger.coverage.model.OperationCoverage;
import ru.viclovsky.swagger.coverage.model.Statistics;
import ru.viclovsky.swagger.coverage.model.SwaggerCoverageResults;

import java.util.*;

public class OperationSwaggerCoverageCalculator extends SwaggerCoverageCalculator {

    private final static Logger LOGGER = Logger.getLogger(OperationSwaggerCoverageCalculator.class);

    private Map<String, Operation> emptyCoverage;
    private Map<String, Operation> partialCoverage;
    private Map<String, Operation> fullCoverage;

    public OperationSwaggerCoverageCalculator(List<SwaggerCoverageFilter> filters, Swagger spec) {
        super(filters, spec);
        this.emptyCoverage = getOperationMap(spec);
        this.partialCoverage = new TreeMap<>();
        this.fullCoverage = new TreeMap<>();
    }

    public OperationSwaggerCoverageCalculator(Swagger spec) {
        super(spec);
        this.emptyCoverage = getOperationMap(spec);
        this.partialCoverage = new TreeMap<>();
        this.fullCoverage = new TreeMap<>();
    }

    @Override
    public OperationSwaggerCoverageCalculator addOutput(Swagger coverageSpec) {
        Map<String, Operation> coverage = getOperationMap(coverageSpec);
        coverage.forEach((k, operation) -> {
            LOGGER.info("Process operation " + k);
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
                return;
            }
            partialCoverage.put(k, op);
        });
        return this;
    }

    @Override
    Object getResults() {
        Statistics statistics = getStatistics();
        SwaggerCoverageResults results = new SwaggerCoverageResults();
        Map<String, OperationCoverage> partialCoverage = new TreeMap<>();

        this.partialCoverage.forEach((k, v) ->
        {
            OperationCoverage problem = new OperationCoverage();
            Set<String> paramsProblem = new TreeSet<>();
            Set<String> statusCodesProblem = new TreeSet<>();

            this.partialCoverage.get(k).getParameters().forEach(parameter ->
                    paramsProblem.add(parameter.getName()));
            this.partialCoverage.get(k).getResponses().forEach((status, resp) ->
                    statusCodesProblem.add(status));

            partialCoverage.put(k, problem
                    .setParams(paramsProblem)
                    .setStatusCodes(statusCodesProblem)
            );
        });

        results.setEmptyCoverage(emptyCoverage.keySet())
                .setFullCoverage(fullCoverage.keySet())
                .setPartialCoverage(partialCoverage)
                .setStatistics(statistics);

        return results;
    }

    /**
     * path + HTTP method = Operation
     */
    private Map<String, Operation> getOperationMap(Swagger swagger) {
        Map<String, Operation> coverage = new TreeMap<>();
        swagger.getPaths().keySet().forEach(path
                -> swagger.getPaths().get(path).getOperationMap().forEach((httpMethod, operation)
                -> coverage.put(String.format("%s %s", path, httpMethod), operation)
        ));
        return coverage;
    }


    private Statistics getStatistics() {
        int emptyCount = emptyCoverage.size();
        int partialCount = partialCoverage.size();
        int fullCount = fullCoverage.size();
        int allCount = emptyCount + partialCount + fullCount;
        return new Statistics().setAllCount(allCount).setEmptyCount(emptyCount)
                .setPartialCount(partialCount).setFullCount(fullCount);
    }


}
