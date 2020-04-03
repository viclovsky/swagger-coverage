package com.github.viclovsky.swagger.coverage.core.results.builder.postbuilder;

import com.github.viclovsky.swagger.coverage.core.generator.SwaggerSpecificationProcessor;
import com.github.viclovsky.swagger.coverage.core.model.OperationKey;
import com.github.viclovsky.swagger.coverage.core.model.OperationsHolder;
import com.github.viclovsky.swagger.coverage.core.results.Results;
import com.github.viclovsky.swagger.coverage.core.results.builder.core.StatisticsOperationPostBuilder;
import com.github.viclovsky.swagger.coverage.core.results.data.CoverageCounter;
import com.github.viclovsky.swagger.coverage.core.results.data.OperationResult;
import com.github.viclovsky.swagger.coverage.core.results.data.TagCoverage;
import com.github.viclovsky.swagger.coverage.core.rule.core.ConditionRule;
import io.swagger.models.Swagger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TagStatisticsBuilder extends StatisticsOperationPostBuilder {

    private static final Logger log = LoggerFactory.getLogger(TagStatisticsBuilder.class);


    protected Swagger swagger;
    protected Map<OperationKey,List<String>> operationToTag;

    protected Map<String, TagCoverage> tagCoverageMap;
    protected CoverageCounter tagCounter = new CoverageCounter();

    @Override
    public TagStatisticsBuilder configure(Swagger swagger, List<ConditionRule> rules) {
        this.swagger = swagger;
        OperationsHolder operations = SwaggerSpecificationProcessor.extractOperation(
            swagger,options.getGeneral().isPathCaseIgnore()
        );

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

        return this;
    }

    @Override
    public void buildOperation(OperationKey operation, OperationResult operationResult) {
       /* log.info("search operation {}",operation.toString());
        if (!operationToTag.containsKey(operation)) {
            return;
        }

        log.info("process operation {}",operation.toString());

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
        */

        operationToTag.entrySet().forEach(entry -> {
            if (operation.toString().equals(entry.getKey().toString())) {
                entry.getValue().stream()
                    .forEach(
                        tag -> tagCoverageMap
                            .get(tag)
                            .incrementByState(operationResult.getState())
                            .updateAllBranchCount(operationResult.getAllBrancheCount())
                            .updateCoveredBranchCount(operationResult.getCoveredBrancheCount())
                            .updateState()
                    );
            }
        });
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
