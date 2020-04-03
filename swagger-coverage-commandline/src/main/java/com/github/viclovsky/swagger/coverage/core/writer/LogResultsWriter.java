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

    private final static Logger log = Logger.getLogger(LogResultsWriter.class);

    public LogResultsWriter() {
    }

    @Override
    public void write(Results results) {
        log.info("Empty coverage: ");
        logOperationCoverage(results.getOperations(),results.getCoverageOperationMap().getEmpty());
        log.info("Partial coverage: ");
        logOperationCoverage(results.getOperations(),results.getCoverageOperationMap().getParty());
        log.info("Full coverage: ");
        logOperationCoverage(results.getOperations(),results.getCoverageOperationMap().getFull());
        logMissedCoverage(results.getMissed());

        DecimalFormat df = new DecimalFormat("###.###");
        float emptyPercentage = (float) (results.getCoverageOperationMap().getEmpty().size() * 100) / results.getOperations().size();
        float partialPercentage = (float) (results.getCoverageOperationMap().getParty().size() * 100) / results.getOperations().size();
        float fullPercentage = (float) (results.getCoverageOperationMap().getFull().size() * 100) / results.getOperations().size();

        log.info(String.format("Conditions: %s/%s", results.getConditionCounter().getCovered(), results.getConditionCounter().getAll()));
        log.info("Empty coverage " + df.format(emptyPercentage) + " %");
        log.info("Partial coverage " + df.format(partialPercentage) + " %");
        log.info("Full coverage " + df.format(fullPercentage) + " %");
    }

    private void logMissedCoverage(Map<OperationKey, Operation> missed) {
        if (!missed.isEmpty()) {
            log.info("Missed coverage: ");
            missed.keySet().forEach(
                    m -> log.info(m.getHttpMethod() + " " + m.getPath()));
        }
    }

    private void logOperationCoverage(Map<OperationKey, OperationResult> operationResults, Set<OperationKey> keys) {
        keys.forEach(operationKey -> {
            if (operationResults.containsKey(operationKey)) {
                printOperationCoverage(operationResults.get(operationKey));
            }
        });
    }

    private void printOperationCoverage(OperationResult result){
        log.info(String.format("%s %s (%s/%s)", result.getOperationKey().getHttpMethod(), result.getOperationKey().getPath(),
                result.getCoveredConditionCount(), result.getAllConditionCount()));

        result.getConditions().forEach(c -> {
            if (c.isCovered()) {
                log.info(String.format("✅ %s", c.getName()));
            } else {
                log.info(String.format("❌ %s", c.getName()));
            }

        });
    }

}
