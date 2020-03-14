package com.github.viclovsky.swagger.coverage.branch.results.builder.postbuilder;

import com.github.viclovsky.swagger.coverage.branch.generator.SwaggerSpecificationProccessor;
import com.github.viclovsky.swagger.coverage.branch.model.OperationsHolder;
import com.github.viclovsky.swagger.coverage.branch.results.Results;
import com.github.viclovsky.swagger.coverage.branch.results.builder.core.StatisticsOperationPostBuilder;
import com.github.viclovsky.swagger.coverage.branch.results.data.CoverageCounter;
import com.github.viclovsky.swagger.coverage.branch.results.data.OperationResult;
import com.github.viclovsky.swagger.coverage.branch.results.data.TagCoverage;
import com.github.viclovsky.swagger.coverage.branch.rule.core.BranchRule;
import io.swagger.models.Swagger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TagStatisticsBuilder extends StatisticsOperationPostBuilder {

    private static final Logger log = LoggerFactory.getLogger(TagStatisticsBuilder.class);


    protected Swagger swagger;
    protected Map<String,List<String>> operationToTag;

    protected Map<String,TagCoverage> tagCoverageMap;
    protected CoverageCounter tagCounter = new CoverageCounter();

    public TagStatisticsBuilder(Swagger swagger, List<BranchRule> rules) {
        super(swagger, rules);
        this.swagger = swagger;
        OperationsHolder operations = SwaggerSpecificationProccessor.extractOperation(swagger);

        tagCoverageMap = swagger
            .getTags()
            .stream()
            .collect(
                Collectors.toMap(
                    tag -> tag.getName(),
                    tag -> new TagCoverage(tag.getDescription())
                )
            );

        operationToTag = operations
            .getOperations()
            .entrySet()
            .stream()
            .collect(
                Collectors.toMap(
                    entry -> entry.getKey(),
                    entry -> entry.getValue().getTags()
                )
            );

        operationToTag
            .entrySet()
            .stream()
            .forEach(
                entry -> entry
                    .getValue()
                    .stream()
                    .forEach(
                        tag ->  tagCoverageMap.get(tag).addOperation(entry.getKey())
                    )
            ) ;
    }

    @Override
    public void buildOperation(String operation, OperationResult operationResult) {
        if (!operationToTag.containsKey(operation)) {
            return;
        }

        operationToTag
            .get(operation)
            .stream()
            .forEach(
                tag -> tagCoverageMap
                    .get(tag)
                    .incrementByState(operationResult.getState())
                    .updateAllBranchCount(operationResult.getAllBrancheCount())
                    .updateCoveredBranchCount(operationResult.getCoveredBrancheCount())
                    .updateState()
            );
    }

    @Override
    public void buildResult(Results results) {
        log.info(tagCoverageMap.toString());

        tagCoverageMap
            .entrySet()
            .stream()
            .forEach(
                entry -> tagCounter.incrementByState(entry.getValue().getState())
            );

        results
            .setTagCoverageMap(tagCoverageMap)
            .setTagCounter(tagCounter)
            ;
    }
}
