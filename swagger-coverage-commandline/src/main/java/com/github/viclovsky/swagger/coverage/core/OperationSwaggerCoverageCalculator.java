package com.github.viclovsky.swagger.coverage.core;

import com.github.viclovsky.swagger.coverage.core.filter.SwaggerCoverageFilter;
import com.github.viclovsky.swagger.coverage.model.Coverage;
import com.github.viclovsky.swagger.coverage.model.OperationCoverage;
import com.github.viclovsky.swagger.coverage.model.Statistics;
import com.github.viclovsky.swagger.coverage.model.SwaggerCoverageResults;
import com.github.viclovsky.swagger.coverage.option.MainOptions;
import io.swagger.models.Swagger;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.util.*;

public class OperationSwaggerCoverageCalculator extends SwaggerCoverageCalculator {

    private final static Logger LOGGER = Logger.getLogger(OperationSwaggerCoverageCalculator.class);

    private Map<String, OperationCoverage> emptyCoverage;
    private Map<String, OperationCoverage> partialCoverage;
    private Map<String, OperationCoverage> fullCoverage;
    private Map<String, OperationCoverage> missedCoverage;

    public OperationSwaggerCoverageCalculator(List<SwaggerCoverageFilter> filters, Swagger spec) {
        super(filters, spec);
        this.emptyCoverage = getOperationMap(spec);
        this.partialCoverage = new TreeMap<>();
        this.fullCoverage = new TreeMap<>();
        this.missedCoverage = new TreeMap<>();
    }

    public OperationSwaggerCoverageCalculator(Swagger spec) {
        super(spec);
        this.emptyCoverage = getOperationMap(spec);
        this.partialCoverage = new TreeMap<>();
        this.fullCoverage = new TreeMap<>();
        this.missedCoverage = new TreeMap<>();
    }

    @Override
    public OperationSwaggerCoverageCalculator addOutput(Swagger coverageSpec) {
        Map<String, OperationCoverage> coverage = getOperationMap(coverageSpec);
        coverage.forEach((k, operation) -> {
            LOGGER.debug("Process operation " + k);
            if (fullCoverage.containsKey(k)) {
                return;
            }
            OperationCoverage op = null;
            if (emptyCoverage.containsKey(k)) {
                op = processOperation(operation, emptyCoverage.get(k));
                emptyCoverage.remove(k);
            }

            if (partialCoverage.containsKey(k)) {
                op = processOperation(operation, partialCoverage.get(k));
                partialCoverage.remove(k);
            }

            if (op == null){
                LOGGER.debug("Operation missed in swagger.json");
                missedCoverage.put(k, operation);
                return;
            }

            if (isEmptyOperation(Objects.requireNonNull(op).getOperation())) {
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
        Map<String, Coverage> partialCoverage = new TreeMap<>();
        Map<String, Coverage> fullCoverage = new TreeMap<>();
        Map<String, Coverage> emptyCoverage = new TreeMap<>();
        Map<String, Coverage> missedCoverage = new TreeMap<>();

        this.fullCoverage.forEach((k, v) -> {
            Coverage problem = new Coverage();
            fullCoverage.put(k, problem
                    .setCoveredParams(v.getCoverage().getCoveredParams())
                    .setCoveredStatusCodes(v.getCoverage().getCoveredStatusCodes())
                    .setIgnoredParams(v.getCoverage().getIgnoredParams())
                    .setIgnoredStatusCodes(v.getCoverage().getIgnoredStatusCodes())
            );
        });

        this.partialCoverage.forEach((k, v) -> {
            Coverage problem = new Coverage();
            Set<String> statusCodesProblem = new TreeSet<>();
            Set<String> paramsProblem = new TreeSet<>();

            this.partialCoverage.get(k).getOperation().getParameters().forEach(parameter ->
                    paramsProblem.add(parameter.getName()));
            this.partialCoverage.get(k).getOperation().getResponses().forEach((status, resp) ->
                    statusCodesProblem.add(status));

            partialCoverage.put(k, problem
                    .setParams(paramsProblem)
                    .setStatusCodes(statusCodesProblem)
                    .setCoveredParams(v.getCoverage().getCoveredParams())
                    .setCoveredStatusCodes(v.getCoverage().getCoveredStatusCodes())
                    .setIgnoredParams(v.getCoverage().getIgnoredParams())
                    .setIgnoredStatusCodes(v.getCoverage().getIgnoredStatusCodes())
            );
        });

        this.emptyCoverage.forEach((k, v) -> {
            Coverage problem = new Coverage();
            Set<String> statusCodesProblem = new TreeSet<>();
            Set<String> paramsProblem = new TreeSet<>();

            this.emptyCoverage.get(k).getOperation().getParameters().forEach(parameter ->
                    paramsProblem.add(parameter.getName()));
            this.emptyCoverage.get(k).getOperation().getResponses().forEach((status, resp) ->
                    statusCodesProblem.add(status));

            emptyCoverage.put(k, problem
                    .setParams(paramsProblem)
                    .setStatusCodes(statusCodesProblem)
                    .setIgnoredParams(v.getCoverage().getIgnoredParams())
                    .setIgnoredStatusCodes(v.getCoverage().getIgnoredStatusCodes())
            );

        });

        this.missedCoverage.forEach((k, v) -> {
            Coverage problem = new Coverage();
            Set<String> statusCodesProblem = new TreeSet<>();
            Set<String> paramsProblem = new TreeSet<>();

            this.missedCoverage.get(k).getOperation().getParameters().forEach(parameter ->
                    paramsProblem.add(parameter.getName()));
            this.missedCoverage.get(k).getOperation().getResponses().forEach((status, resp) ->
                    statusCodesProblem.add(status));

            missedCoverage.put(k, problem
                    .setParams(paramsProblem)
                    .setStatusCodes(statusCodesProblem)
                    .setIgnoredParams(v.getCoverage().getIgnoredParams())
                    .setIgnoredStatusCodes(v.getCoverage().getIgnoredStatusCodes())
            );

        });

        results.setEmptyCoverage(emptyCoverage)
                .setFullCoverage(fullCoverage)
                .setPartialCoverage(partialCoverage)
                .setMissedCoverage(missedCoverage)
                .setStatistics(statistics);

        printResults(results);
        return results;
    }

    private void printResults(SwaggerCoverageResults results) {
        if (!results.getEmptyCoverage().isEmpty()) {
            LOGGER.info("Empty coverage: ");
            results.getEmptyCoverage().keySet().forEach(k -> LOGGER.info("   " + k));
        }

        if (!results.getPartialCoverage().isEmpty()) {
            LOGGER.info("Partial coverage (status code or parameter absent): ");
            results.getPartialCoverage().forEach(
                    (k, v) -> {
                        LOGGER.info("   " + k);
                        if (!v.getParams().isEmpty()) {
                            LOGGER.info("       " + v.getParams());
                        }

                        if (!v.getStatusCodes().isEmpty()) {
                            LOGGER.info("       " + v.getStatusCodes());
                        }
                    }
            );
        }
        if (!results.getFullCoverage().isEmpty()) {
            LOGGER.info("Full coverage: ");
            results.getFullCoverage().forEach((k, v) -> LOGGER.info("   " + k));
        }
        if (!results.getMissedCoverage().isEmpty()) {
            LOGGER.info("Operation missed in swagger: ");
            results.getMissedCoverage().forEach((k, v) -> LOGGER.info("   " + k));
        }
        DecimalFormat df = new DecimalFormat("###.###");
        float emptyPercentage = (float) (results.getStatistics().getEmpty() * 100) / results.getStatistics().getAll();
        float partialPercentage = (float) (results.getStatistics().getPartial() * 100) / results.getStatistics().getAll();
        float fullPercentage = (float) (results.getStatistics().getFull() * 100) / results.getStatistics().getAll();
        LOGGER.info("Empty coverage " + df.format(emptyPercentage) + " %");
        LOGGER.info("Partial coverage " + df.format(partialPercentage) + " %");
        LOGGER.info("Full coverage " + df.format(fullPercentage) + " %");
        LOGGER.info("Missed in swagger " + results.getStatistics().getMissed() + " operation");
}

    /**
     * path + HTTP method = Operation
     */
    private Map<String, OperationCoverage> getOperationMap(Swagger swagger) {
        Map<String, OperationCoverage> coverage;
        if (MainOptions.getIgnoreRestCase()) {
            coverage = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        } else {
            coverage = new TreeMap<>();
        }
        swagger.getPaths().keySet().forEach(path
                -> swagger.getPaths().get(path).getOperationMap().forEach((httpMethod, operation)
                -> coverage.put(String.format("%s %s", path, httpMethod), new OperationCoverage(operation))
        ));
        return coverage;
    }

    public OperationCoverage processOperation(OperationCoverage current, OperationCoverage expected) {
        if (!filters.isEmpty()) {
            filters.forEach(filter -> filter.apply(expected));
        }

        if (Objects.nonNull(current.getOperation().getResponses())) {
            current.getOperation().getResponses().forEach((status, resp) -> {
                LOGGER.debug(String.format("Remove status code: [%s]", status));
                expected.getOperation().getResponses().remove(status);
                expected.addCoveredStatusCode(status);
            });
        }

        if (Objects.nonNull(current.getOperation().getParameters())) {
            current.getOperation().getParameters().forEach(parameter ->
                    expected.getOperation().getParameters().stream()
                            .filter(equalsParam(parameter).or(isBody(parameter))).findFirst()
                            .ifPresent(presentParam -> {
                                LOGGER.debug(String.format("Remove %s: [%s]", presentParam.getIn(),
                                        presentParam.getName()));
                                expected.getOperation().getParameters().remove(presentParam);
                                expected.addCoveredParameter(presentParam.getName());
                            }));
        }
        return expected;
    }

    private Statistics getStatistics() {
        int emptyCount = emptyCoverage.size();
        int partialCount = partialCoverage.size();
        int fullCount = fullCoverage.size();
        int missedCount = missedCoverage.size();
        int allCount = emptyCount + partialCount + fullCount;
        return new Statistics().setAllCount(allCount).setEmptyCount(emptyCount)
                .setPartialCount(partialCount).setFullCount(fullCount).setMissed(missedCount);
    }


}
