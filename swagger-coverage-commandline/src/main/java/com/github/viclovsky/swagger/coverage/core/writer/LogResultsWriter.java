package com.github.viclovsky.swagger.coverage.core.writer;

import com.github.viclovsky.swagger.coverage.core.model.OperationKey;
import com.github.viclovsky.swagger.coverage.core.results.OperationResult;
import com.github.viclovsky.swagger.coverage.core.results.Results;
import io.swagger.models.Operation;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.util.Map;

public class LogResultsWriter implements CoverageResultsWriter {

    private final static Logger log = Logger.getLogger(LogResultsWriter.class);

    public LogResultsWriter() {
    }

    @Override
    public void write(Results results) {
        log.warn("Empty coverage: ");
        logOperationCoverage(results.getEmpty());
        log.warn("Partial coverage: ");
        logOperationCoverage(results.getPartial());
        log.warn("Full coverage: ");
        logOperationCoverage(results.getFull());
        logMissedCoverage(results.getMissed());

        DecimalFormat df = new DecimalFormat("###.###");
        float emptyPercentage = (float) (results.getEmptyOperationCount() * 100) / results.getAllOperationCount();
        float partialPercentage = (float) (results.getPartialOperationCount() * 100) / results.getAllOperationCount();
        float fullPercentage = (float) (results.getFullOperationCount() * 100) / results.getAllOperationCount();

        log.info(String.format("Conditions: %s/%s", results.getCoveredConditionCount(), results.getAllConditionCount()));
        log.info("Empty coverage " + df.format(emptyPercentage) + " %");
        log.info("Partial coverage " + df.format(partialPercentage) + " %");
        log.info("Full coverage " + df.format(fullPercentage) + " %");
    }

    private void logMissedCoverage(Map<OperationKey, Operation> missed) {
        if (!missed.isEmpty()) {
            log.warn("Missed coverage: ");
            missed.keySet().forEach(
                    m -> log.warn(m.getHttpMethod() + " " + m.getPath()));
        }
    }

    private void logOperationCoverage(Map<OperationKey, OperationResult> operationResults) {
        if (!operationResults.isEmpty()) {
            operationResults.forEach(
                    ((key, value) -> {
                        log.info(String.format("%s %s (%s/%s)", key.getHttpMethod(), key.getPath(),
                                value.getCoveredConditionCount(), value.getAllConditionCount()));
                        value.getConditions().forEach(c -> {
                            if (c.isCovered()) {
                                log.info(String.format("✅ %s", c.getName()));
                            } else {
                                log.info(String.format("❌ %s", c.getName()));
                            }

                        });
                    }));
        }
    }
}
