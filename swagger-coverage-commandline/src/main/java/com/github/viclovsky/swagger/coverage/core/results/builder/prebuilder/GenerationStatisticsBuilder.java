package com.github.viclovsky.swagger.coverage.core.results.builder.prebuilder;

import com.github.viclovsky.swagger.coverage.CommandLine;
import com.github.viclovsky.swagger.coverage.configuration.Configuration;
import com.github.viclovsky.swagger.coverage.core.results.Results;
import com.github.viclovsky.swagger.coverage.core.results.builder.core.StatisticsPreBuilder;
import com.github.viclovsky.swagger.coverage.core.results.data.GenerationStatistics;
import com.github.viclovsky.swagger.coverage.core.results.util.DateTimeUtil;
import com.github.viclovsky.swagger.coverage.core.rule.core.ConditionRule;
import io.swagger.v3.oas.models.OpenAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.List;

public class GenerationStatisticsBuilder extends StatisticsPreBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandLine.class);

    private long fileCounter = 0;
    private FileTime minResultTime = null;
    private FileTime maxResultTime = null;
    private long startTime;

    @Override
    public GenerationStatisticsBuilder configure(OpenAPI swagger, List<ConditionRule> rules) {
        startTime = System.currentTimeMillis();
        return this;
    }

    @Override
    public GenerationStatisticsBuilder add(String path) {
        Path file = Paths.get(path);
        this.fileCounter++;

        try {
            BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
            if (minResultTime == null || minResultTime.toMillis() > attr.lastModifiedTime().toMillis()) {
                minResultTime = attr.lastModifiedTime();
            }

            if (maxResultTime == null || maxResultTime.toMillis() < attr.lastModifiedTime().toMillis()) {
                maxResultTime = attr.lastModifiedTime();
            }
        } catch (IOException e) {
            LOGGER.error("can't read file attributes", e);
        }
        return this;
    }

    @Override
    public void build(Results results, Configuration configuration) {
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
