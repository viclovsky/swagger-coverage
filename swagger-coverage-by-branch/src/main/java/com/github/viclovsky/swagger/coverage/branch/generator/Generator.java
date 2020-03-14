package com.github.viclovsky.swagger.coverage.branch.generator;

import com.github.viclovsky.swagger.coverage.CoverageOutputReader;
import com.github.viclovsky.swagger.coverage.CoverageResultsWriter;
import com.github.viclovsky.swagger.coverage.FileSystemOutputReader;
import com.github.viclovsky.swagger.coverage.branch.model.Branch;
import com.github.viclovsky.swagger.coverage.branch.model.BranchOperationCoverage;
import com.github.viclovsky.swagger.coverage.branch.model.OperationsHolder;
import com.github.viclovsky.swagger.coverage.branch.results.GenerationStatistics;
import com.github.viclovsky.swagger.coverage.branch.results.Results;
import com.github.viclovsky.swagger.coverage.branch.rule.core.BranchRule;
import com.github.viclovsky.swagger.coverage.branch.rule.parameter.*;
import com.github.viclovsky.swagger.coverage.branch.rule.status.HTTPStatusBranchRule;
import com.github.viclovsky.swagger.coverage.branch.rule.status.OnlyDeclaretedHTTPStatuses;
import com.github.viclovsky.swagger.coverage.branch.writer.HtmlBranchReportResultsWriter;
import io.swagger.models.Operation;
import io.swagger.models.Swagger;
import io.swagger.models.parameters.Parameter;
import io.swagger.parser.SwaggerParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Generator {

    private static final Logger log = LoggerFactory.getLogger(Generator.class);

    private Path specPath;
    private Path inputPath;
    protected Map<String, BranchOperationCoverage> mainCoverageData;
    protected Map<String, Operation> missed  = new TreeMap<>();

    protected long fileCounter = 0;

    SwaggerParser parser = new SwaggerParser();

    protected FileTime minResultTime = null;
    protected FileTime maxResultTime = null;


    public void run(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneId.systemDefault())
            ;

        long startTime = System.currentTimeMillis();
        Swagger spec = parser.read(getSpecPath().toString());

        log.info("spec is {}",spec);

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
                .forEach(o -> processFile(o.toString()));

        CoverageResultsWriter writer = new HtmlBranchReportResultsWriter();

        Results result = new Results(mainCoverageData)
            .setMissed(missed)
            .setGenerationStatistics(
                new GenerationStatistics()
                    .setResultFileCount(fileCounter)
                    .setGenerationTime(System.currentTimeMillis() - startTime)
                    .setFileResultDateInterval(dateTimeFormatter.format(minResultTime.toInstant()) + " - " + dateTimeFormatter.format(maxResultTime.toInstant()))
                    .setGenerateDate(dateTimeFormatter.format(Instant.now()))
                )
            ;
        writer.write(result);
    }

    public void processFile(String path) {
        Path file = Paths.get(path);

        try {
            BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
            if (minResultTime == null || minResultTime.toMillis() > attr.lastModifiedTime().toMillis() ) {
                minResultTime = attr.lastModifiedTime();
            }

            if (maxResultTime == null || maxResultTime.toMillis() < attr.lastModifiedTime().toMillis() ) {
                maxResultTime = attr.lastModifiedTime();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }



        processResult(parser.read(path));
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
                    .increaseProcessCount()
                    .getBranches()
                    .stream()
                    .filter(Branch::isNeedCheck)
                    .forEach(branch -> branch.check(currentParams,entry.getValue().getResponses()))
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
