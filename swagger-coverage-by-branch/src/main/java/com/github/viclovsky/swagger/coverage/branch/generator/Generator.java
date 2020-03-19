package com.github.viclovsky.swagger.coverage.branch.generator;

import com.github.viclovsky.swagger.coverage.CoverageOutputReader;
import com.github.viclovsky.swagger.coverage.CoverageResultsWriter;
import com.github.viclovsky.swagger.coverage.FileSystemOutputReader;
import com.github.viclovsky.swagger.coverage.branch.model.BranchOperationCoverage;
import com.github.viclovsky.swagger.coverage.branch.model.OperationsHolder;
import com.github.viclovsky.swagger.coverage.branch.predicate.BranchPredicate;
import com.github.viclovsky.swagger.coverage.branch.results.GenerationStatistics;
import com.github.viclovsky.swagger.coverage.branch.results.Results;
import com.github.viclovsky.swagger.coverage.branch.rule.core.BranchRule;
import com.github.viclovsky.swagger.coverage.branch.rule.parameter.EmptyHeaderBranchRule;
import com.github.viclovsky.swagger.coverage.branch.rule.parameter.EnumValuesBranchRule;
import com.github.viclovsky.swagger.coverage.branch.rule.parameter.NotEmptyBodyBranchRule;
import com.github.viclovsky.swagger.coverage.branch.rule.parameter.NotOnlyEnumValuesBranchRule;
import com.github.viclovsky.swagger.coverage.branch.rule.parameter.SimpleParameterBranchRule;
import com.github.viclovsky.swagger.coverage.branch.rule.status.HTTPStatusBranchRule;
import com.github.viclovsky.swagger.coverage.branch.rule.status.OnlyDeclaretedHTTPStatuses;
import com.github.viclovsky.swagger.coverage.branch.writer.HtmlBranchReportResultsWriter;
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

    private Map<String, BranchOperationCoverage> mainCoverageData;
    private Map<String, Operation> missed  = new TreeMap<>();
    private long fileCounter = 0;

    public void run() {
        long startTime = System.currentTimeMillis();
        SwaggerParser parser = new SwaggerParser();
        Swagger spec = parser.read(getSpecPath().toString());

        log.info("spec is {}", spec);

        List<BranchRule> rules = new ArrayList<>();
        rules.add(new SimpleParameterBranchRule());
        rules.add(new EmptyHeaderBranchRule());
        rules.add(new NotEmptyBodyBranchRule());
        rules.add(new HTTPStatusBranchRule());
        rules.add(new OnlyDeclaretedHTTPStatuses());
        rules.add(new EnumValuesBranchRule());
        rules.add(new NotOnlyEnumValuesBranchRule());

        mainCoverageData = OperationBranchGenerator.getOperationMap(spec,rules);

        CoverageOutputReader reader = new FileSystemOutputReader(getInputPath());
        reader.getOutputs()
                .forEach(o -> processResult(parser.read(o.toString())));

        CoverageResultsWriter writer = new HtmlBranchReportResultsWriter();

        Results result = new Results(mainCoverageData).setMissed(missed)
                .setGenerationStatistics(new GenerationStatistics()
                    .setResultFileCount(fileCounter)
                        .setGenerationTime(System.currentTimeMillis() - startTime));
        writer.write(result);
    }

    private void processResult(Swagger swagger) {
        fileCounter++;

        OperationsHolder operations = SwaggerSpecificationProcessor.extractOperation(swagger);

        operations.getOperations().entrySet().stream().forEach(entry -> {
            log.info(String.format("==  process result %s", entry.getKey()));

            List<Parameter> currentParams = entry.getValue().getParameters();

            log.info(String.format("current param map is %s",currentParams));

            if (mainCoverageData.containsKey(entry.getKey())) {
                mainCoverageData
                    .get(entry.getKey())
                    .getBranches()
                    .stream()
                        .filter(t -> !t.isCovered())
                    .forEach(branch -> {
                        boolean isCover = true;
                        for(BranchPredicate bp:  branch.getPredicateList()){
                            isCover = isCover
                                && bp.check(
                                    currentParams,
                                    entry.getValue().getResponses()
                                )
                            ;

                            log.info(String.format(" === predicate [%s] is [%s]",branch.getName(),isCover));
                        }

                        branch.setCovered(isCover);
                    })
                ;
            } else {
                missed.put(
                    entry.getKey(),
                    entry.getValue()
                );
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
