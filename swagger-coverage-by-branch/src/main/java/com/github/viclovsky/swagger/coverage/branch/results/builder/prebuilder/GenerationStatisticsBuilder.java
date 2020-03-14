package com.github.viclovsky.swagger.coverage.branch.results.builder.prebuilder;

import com.github.viclovsky.swagger.coverage.branch.results.Results;
import com.github.viclovsky.swagger.coverage.branch.results.builder.core.StatisticsPreBuilder;
import com.github.viclovsky.swagger.coverage.branch.results.data.GenerationStatistics;
import com.github.viclovsky.swagger.coverage.branch.results.util.DateTimeUtil;
import com.github.viclovsky.swagger.coverage.branch.rule.core.BranchRule;
import io.swagger.models.Swagger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.List;

public class GenerationStatisticsBuilder extends StatisticsPreBuilder {
    protected long fileCounter = 0;
    protected FileTime minResultTime = null;
    protected FileTime maxResultTime = null;
    protected long startTime;

    public GenerationStatisticsBuilder(Swagger swagger, List<BranchRule> rules) {
        super(swagger,rules);
        startTime = System.currentTimeMillis();
    }

    @Override
    public GenerationStatisticsBuilder add(String path){
        Path file = Paths.get(path);
        this.fileCounter++;

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

        return this;
    }

    @Override
    public void build(Results results){
        final long duration = System.currentTimeMillis() - startTime;
        final String resultDateDuration = DateTimeUtil.formatDate(minResultTime.toInstant())
            + " - "
            + DateTimeUtil.formatDate(maxResultTime.toInstant());

        results.setGenerationStatistics(
            new GenerationStatistics()
                .setResultFileCount(fileCounter)
                .setGenerationTime(duration)
                .setFileResultDateInterval(resultDateDuration)
                .setGenerateDate(DateTimeUtil.formatDate(Instant.now()))
        );
    }

}
