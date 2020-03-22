package com.github.viclovsky.swagger.coverage.core.generator;

import com.github.viclovsky.swagger.coverage.CoverageOutputReader;
import com.github.viclovsky.swagger.coverage.CoverageResultsWriter;
import com.github.viclovsky.swagger.coverage.FileSystemOutputReader;
import com.github.viclovsky.swagger.coverage.HtmlReportResultsWriter;
import com.github.viclovsky.swagger.coverage.core.model.BranchOperationCoverage;
import com.github.viclovsky.swagger.coverage.core.model.OperationKey;
import com.github.viclovsky.swagger.coverage.core.model.OperationsHolder;
import com.github.viclovsky.swagger.coverage.core.predicate.BranchPredicate;
import com.github.viclovsky.swagger.coverage.core.results.GenerationStatistics;
import com.github.viclovsky.swagger.coverage.core.results.Results;
import com.github.viclovsky.swagger.coverage.core.rule.BranchRule;
import com.github.viclovsky.swagger.coverage.core.rule.parameter.DefaultBodyBranchRule;
import com.github.viclovsky.swagger.coverage.core.rule.parameter.DefaultEnumValuesBranchRule;
import com.github.viclovsky.swagger.coverage.core.rule.parameter.DefaultParameterBranchRule;
import com.github.viclovsky.swagger.coverage.core.rule.status.DefaultHTTPStatusBranchRule;
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

    private Map<OperationKey, BranchOperationCoverage> mainCoverageData;
    private Map<OperationKey, Operation> missed = new TreeMap<>();
    private long fileCounter = 0;

    public void run() {
        long startTime = System.currentTimeMillis();
        SwaggerParser parser = new SwaggerParser();
        Swagger spec = parser.read(getSpecPath().toString());

        log.info("spec is {}", spec);

        List<BranchRule> rules = new ArrayList<>();
        //by default
        rules.add(new DefaultParameterBranchRule());
        rules.add(new DefaultBodyBranchRule());
        rules.add(new DefaultHTTPStatusBranchRule());
        rules.add(new DefaultEnumValuesBranchRule());

        mainCoverageData = OperationBranchGenerator.getOperationMap(spec, rules);

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
                mainCoverageData.get(key).getBranches().stream().filter(t -> !t.isCovered())
                        .forEach(branch -> {
                            boolean isCover = true;
                            for (BranchPredicate bp : branch.getPredicateList()) {
                                isCover = isCover && bp.check(currentParams, value.getResponses());
                                log.info(String.format(" === predicate [%s] is [%s]", branch.getName(), isCover));
                            }
                            branch.setCovered(isCover);
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
