package com.github.viclovsky.swagger.coverage.core.writer;

import com.github.viclovsky.swagger.coverage.core.model.OperationKey;
import com.github.viclovsky.swagger.coverage.core.results.Results;
import com.github.viclovsky.swagger.coverage.core.results.data.OperationResult;
import io.swagger.models.Operation;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.Set;

public class LogResultsWriter implements CoverageResultsWriter {

    private final static Logger LOGGER = Logger.getLogger(LogResultsWriter.class);

    public LogResultsWriter() {
    }

    @Override
    public void write(Results results) {
        LOGGER.info("Deprecated coverage: ");
        logOperationCoverage(results.getOperations(), results.getCoverageOperationMap().getDeprecated());
        LOGGER.info("Empty coverage: ");
        logOperationCoverage(results.getOperations(), results.getCoverageOperationMap().getEmpty());
        LOGGER.info("Partial coverage: ");
        logOperationCoverage(results.getOperations(), results.getCoverageOperationMap().getParty());
        LOGGER.info("Full coverage: ");
        logOperationCoverage(results.getOperations(), results.getCoverageOperationMap().getFull());
        logMissedCoverage(results.getMissed());

        DecimalFormat df = new DecimalFormat("###.###");
        float deprecatedPercentage = (float) (results.getCoverageOperationMap().getDeprecated().size() * 100) / results.getOperations().size();
        float emptyPercentage = (float) (results.getCoverageOperationMap().getEmpty().size() * 100) / results.getOperations().size();
        float partialPercentage = (float) (results.getCoverageOperationMap().getParty().size() * 100) / results.getOperations().size();
        float fullPercentage = (float) (results.getCoverageOperationMap().getFull().size() * 100) / results.getOperations().size();

        LOGGER.info(String.format("Conditions: %s/%s", results.getConditionCounter().getCovered(), results.getConditionCounter().getAll()));
        LOGGER.info("Deprecated coverage " + df.format(deprecatedPercentage) + " %");
        LOGGER.info("Empty coverage " + df.format(emptyPercentage) + " %");
        LOGGER.info("Partial coverage " + df.format(partialPercentage) + " %");
        LOGGER.info("Full coverage " + df.format(fullPercentage) + " %");
    }

    private void logMissedCoverage(Map<OperationKey, Operation> missed) {
        if (!missed.isEmpty()) {
            LOGGER.info("Missed coverage: ");
            missed.keySet().forEach(
                    m -> LOGGER.info(m.getHttpMethod() + " " + m.getPath()));
        }
    }

    private void logOperationCoverage(Map<OperationKey, OperationResult> operationResults, Set<OperationKey> keys) {
        keys.forEach(operationKey -> {
            if (operationResults.containsKey(operationKey)) {
                printOperationCoverage(operationResults.get(operationKey));
            }
        });
    }

    private void printOperationCoverage(OperationResult result) {
        LOGGER.info(String.format("%s %s (%s/%s)", result.getOperationKey().getHttpMethod(), result.getOperationKey().getPath(),
                result.getCoveredConditionCount(), result.getAllConditionCount()));

        result.getConditions().forEach(c -> {
            if (c.isCovered()) {
                LOGGER.info(String.format("✅ %s", c.getName()));
            } else {
                LOGGER.info(String.format("❌ %s", c.getName()));
            }

        });
    }

}
