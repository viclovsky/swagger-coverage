package com.github.viclovsky.swagger.coverage.core.results.builder.postbuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.viclovsky.swagger.coverage.core.results.Results;
import com.github.viclovsky.swagger.coverage.core.results.builder.core.StatisticsBuilder;
import com.github.viclovsky.swagger.coverage.core.results.builder.core.StatisticsPostBuilder;
import com.github.viclovsky.swagger.coverage.core.rule.core.ConditionRule;
import io.swagger.models.Swagger;

import java.util.List;

public class ConfigurationStatisticsBuilder extends StatisticsPostBuilder {
    @Override
    public StatisticsBuilder configure(Swagger swagger, List<ConditionRule> rules) {
        return this;
    }

    @Override
    public void build(Results results) {
        ObjectMapper mapper = new ObjectMapper();
        String prettyConfiguration = "";
        try {
            prettyConfiguration = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(options);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        results.setPrettyConfiguration(prettyConfiguration);
    }
}
