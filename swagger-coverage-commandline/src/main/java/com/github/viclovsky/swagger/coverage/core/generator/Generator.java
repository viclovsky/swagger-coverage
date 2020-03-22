package com.github.viclovsky.swagger.coverage.core.generator;

import com.github.viclovsky.swagger.coverage.CoverageOutputReader;
import com.github.viclovsky.swagger.coverage.CoverageResultsWriter;
import com.github.viclovsky.swagger.coverage.FileSystemOutputReader;
import com.github.viclovsky.swagger.coverage.HtmlReportResultsWriter;
import com.github.viclovsky.swagger.coverage.core.model.ConditionOperationCoverage;
import com.github.viclovsky.swagger.coverage.core.model.OperationKey;
import com.github.viclovsky.swagger.coverage.core.model.OperationsHolder;
import com.github.viclovsky.swagger.coverage.core.predicate.ConditionPredicate;
import com.github.viclovsky.swagger.coverage.core.results.GenerationStatistics;
import com.github.viclovsky.swagger.coverage.core.results.Results;
import com.github.viclovsky.swagger.coverage.core.rule.ConditionRule;
import com.github.viclovsky.swagger.coverage.core.rule.DefaultOperationConditionRule;
import com.github.viclovsky.swagger.coverage.core.rule.parameter.DefaultBodyConditionRule;
import com.github.viclovsky.swagger.coverage.core.rule.parameter.DefaultEnumValuesConditionRule;
import com.github.viclovsky.swagger.coverage.core.rule.parameter.DefaultParameterConditionRule;
import com.github.viclovsky.swagger.coverage.core.rule.status.DefaultHTTPStatusConditionRule;
import io.swagger.models.Operation;
import io.swagger.models.Swagger;
import io.swagger.models.parameters.Parameter;
import io.swagger.parser.SwaggerParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Generator {

    private static final Logger log = LoggerFactory.getLogger(Generator.class);

    private Path specPath;
    private Path inputPath;

    private Map<OperationKey, ConditionOperationCoverage> mainCoverageData;
    private Map<OperationKey, Operation> missed = new TreeMap<>();
    private long fileCounter = 0;

    public void run() {
        long startTime = System.currentTimeMillis();
        SwaggerParser parser = new SwaggerParser();
        Swagger spec = parser.read(getSpecPath().toString());

        log.info("spec is {}", spec);

        List<ConditionRule> rules = new ArrayList<>();
        //by default
        rules.add(new DefaultOperationConditionRule());
        rules.add(new DefaultParameterConditionRule());
        rules.add(new DefaultBodyConditionRule());
        rules.add(new DefaultHTTPStatusConditionRule());
        rules.add(new DefaultEnumValuesConditionRule());

        mainCoverageData = OperationConditionGenerator.getOperationMap(spec, rules);

        CoverageOutputReader reader = new FileSystemOutputReader(getInputPath());
        reader.getOutputs().forEach(o -> processResult(parser.read(o.toString())));

        CoverageResultsWriter writer = new HtmlReportResultsWriter();

        Results result = new Results(mainCoverageData).setMissed(missed)
                .setGenerationStatistics(new GenerationStatistics()
                        .setResultFileCount(fileCounter)
                        .setGenerationTime(System.currentTimeMillis() - startTime))
                .setInfo(spec.getInfo());
        writer.write(result);
    }

    private void processResult(Swagger swagger) {
        fileCounter++;
        OperationsHolder operations = SwaggerSpecificationProcessor.extractOperation(swagger);
        operations.getOperations().forEach((key, value) -> {
            log.info(String.format("==  process result %s", key));

            List<Parameter> currentParams = value.getParameters();

            log.info(String.format("current param map is %s", currentParams));

            if (mainCoverageData.containsKey(key)) {
                mainCoverageData.get(key).getConditions().stream().filter(t -> !t.isCovered())
                        .forEach(condition -> {
                            boolean isCover = true;
                            for (ConditionPredicate bp : condition.getPredicateList()) {
                                isCover = isCover && bp.check(currentParams, value.getResponses());
                                log.info(String.format(" === predicate [%s] is [%s]", condition.getName(), isCover));
                            }
                            condition.setCovered(isCover);
                        });
            } else {
                missed.put(key, value);
            }
        });
    }

    public Path getSpecPath() {
        return specPath;
    }

    public Generator setSpecPath(Path specPath) {
        this.specPath = specPath;
        return this;
    }

    public Path getInputPath() {
        return inputPath;
    }

    public Generator setInputPath(Path inputPath) {
        this.inputPath = inputPath;
        return this;
    }
}
