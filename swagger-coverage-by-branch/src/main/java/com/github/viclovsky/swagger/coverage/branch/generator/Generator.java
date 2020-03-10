package com.github.viclovsky.swagger.coverage.branch.generator;

import com.github.viclovsky.swagger.coverage.*;
import com.github.viclovsky.swagger.coverage.branch.model.Branch;
import com.github.viclovsky.swagger.coverage.branch.model.BranchOperationCoverage;
import com.github.viclovsky.swagger.coverage.branch.model.OperationsHolder;
import com.github.viclovsky.swagger.coverage.branch.results.GenerationStatistics;
import com.github.viclovsky.swagger.coverage.branch.results.Results;
import com.github.viclovsky.swagger.coverage.branch.predicate.BranchPredicate;
import com.github.viclovsky.swagger.coverage.branch.rule.core.BranchRule;
import com.github.viclovsky.swagger.coverage.branch.rule.parameter.EmptyHeaderBranchRule;
import com.github.viclovsky.swagger.coverage.branch.rule.parameter.NotEmptyBodyBranchRule;
import com.github.viclovsky.swagger.coverage.branch.rule.parameter.SimpleParameterBranchRule;
import com.github.viclovsky.swagger.coverage.branch.rule.status.HTTPStatusBranchRule;
import com.github.viclovsky.swagger.coverage.branch.rule.status.OnlyDeclaretedHTTPStatuses;
import com.github.viclovsky.swagger.coverage.branch.writer.HtmlBranchReportResultsWriter;
import io.swagger.models.Operation;
import io.swagger.models.parameters.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Generator {

    private static final Logger log = LoggerFactory.getLogger(Generator.class);

    private Path specPath;
    private Path inputPath;
    protected Map<String, BranchOperationCoverage> mainCoverageData;
    protected Map<String, Operation> missed  = new TreeMap<>();

    protected long fileCounter = 0;

    public void run(){
        long startTime = System.currentTimeMillis();
        SwaggerParser parser = new SwaggerParser();
        Swagger spec = parser.read(getSpecPath().toString());

        log.info("spec is {}",spec);

        List<BranchRule> rules = new ArrayList<>();
        rules.add(new SimpleParameterBranchRule());
        rules.add(new EmptyHeaderBranchRule());
        rules.add(new NotEmptyBodyBranchRule());
        rules.add(new HTTPStatusBranchRule());
        rules.add(new OnlyDeclaretedHTTPStatuses());

        mainCoverageData = OperationBranchGenerator.getOperationMap(spec,rules);

        CoverageOutputReader reader = new FileSystemOutputReader(getInputPath());
        reader.getOutputs()
                .forEach(o -> processResult(parser.read(o.toString())));

        CoverageResultsWriter writer = new HtmlBranchReportResultsWriter();

        Results result = new Results(mainCoverageData)
            .setMissed(missed)
            .setGenerationStatistics(
                new GenerationStatistics()
                    .setResultFileCount(fileCounter)
                    .setGenerationTime(System.currentTimeMillis() - startTime)
                )
            ;
        writer.write(result);
    }

    public static String extractValue(Parameter p){
        if (p.getVendorExtensions() == null) {
            return p.getName();
        }

        if (p.getVendorExtensions().containsKey("x-example")) {
            return (String) p.getVendorExtensions().get("x-example");
        }

        return p.getName();
    }

    public void processResult(Swagger swagger) {
        fileCounter++;

        OperationsHolder operations = SwaggerSpecificationProccessor.extractOperation(swagger);

        operations.getOperations().entrySet().stream().forEach(entry -> {
            log.info(String.format("==  process result %s", entry.getKey()));

            Map<String,Parameter> currentParams = entry.getValue().getParameters().stream().collect(Collectors.toMap(
                Parameter::getName,
                p->p
            ));

            log.info(String.format("current param map is %s",currentParams));

            if (mainCoverageData.containsKey(entry.getKey())) {
                mainCoverageData
                    .get(entry.getKey())
                    .getBranches()
                    .stream()
                    .filter(Predicate.not(Branch::isCovered))
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
