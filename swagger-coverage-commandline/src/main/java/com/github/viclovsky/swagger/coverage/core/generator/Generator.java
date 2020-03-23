package com.github.viclovsky.swagger.coverage.core.generator;

import com.github.viclovsky.swagger.coverage.CoverageOutputReader;
import com.github.viclovsky.swagger.coverage.FileSystemOutputReader;
import com.github.viclovsky.swagger.coverage.core.config.Configuration;
import com.github.viclovsky.swagger.coverage.core.model.ConditionOperationCoverage;
import com.github.viclovsky.swagger.coverage.core.model.OperationKey;
import com.github.viclovsky.swagger.coverage.core.model.OperationsHolder;
import com.github.viclovsky.swagger.coverage.core.predicate.ConditionPredicate;
import com.github.viclovsky.swagger.coverage.core.results.GenerationStatistics;
import com.github.viclovsky.swagger.coverage.core.results.Results;
import io.swagger.models.Operation;
import io.swagger.models.Swagger;
import io.swagger.models.parameters.Parameter;
import io.swagger.parser.SwaggerParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Generator {

    private static final Logger log = LoggerFactory.getLogger(Generator.class);

    private Configuration configuration;

    private Map<OperationKey, ConditionOperationCoverage> mainCoverageData;
    private Map<OperationKey, Operation> missed = new TreeMap<>();
    private long fileCounter = 0;

    public void run() {
        long startTime = System.currentTimeMillis();
        SwaggerParser parser = new SwaggerParser();
        Swagger spec = parser.read(configuration.getSpecPath().toString());

        log.debug("spec is {}", spec);

        mainCoverageData = OperationConditionGenerator.getOperationMap(spec, configuration.getRules());

        CoverageOutputReader reader = new FileSystemOutputReader(configuration.getInputPath());
        reader.getOutputs().forEach(o -> processResult(parser.read(o.toString())));

        Results result = new Results(mainCoverageData).setMissed(missed)
                .setGenerationStatistics(new GenerationStatistics()
                        .setResultFileCount(fileCounter)
                        .setGenerationTime(System.currentTimeMillis() - startTime))
                .setInfo(spec.getInfo());

        configuration.getWriters().forEach(w -> w.write(result));
    }

    private void processResult(Swagger swagger) {
        fileCounter++;
        OperationsHolder operations = SwaggerSpecificationProcessor.extractOperation(swagger);
        operations.getOperations().forEach((key, value) -> {
            log.debug(String.format("==  process result %s", key));

            List<Parameter> currentParams = value.getParameters();

            log.debug(String.format("current param map is %s", currentParams));

            if (mainCoverageData.containsKey(key)) {
                mainCoverageData.get(key).getConditions().stream().filter(t -> !t.isCovered())
                        .forEach(condition -> {
                            boolean isCover = true;
                            for (ConditionPredicate conditionPredicate : condition.getPredicateList()) {
                                isCover = isCover && conditionPredicate.check(currentParams, value.getResponses());
                                log.debug(String.format(" === predicate [%s] is [%s]", condition.getName(), isCover));
                            }
                            condition.setCovered(isCover);
                        });
            } else {
                missed.put(key, value);
            }
        });
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public Generator setConfiguration(Configuration configuration) {
        this.configuration = configuration;
        return this;
    }
}
