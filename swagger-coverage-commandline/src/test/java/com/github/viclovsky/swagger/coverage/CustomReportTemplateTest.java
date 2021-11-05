package com.github.viclovsky.swagger.coverage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.viclovsky.swagger.coverage.configuration.options.ConfigurationOptions;
import com.github.viclovsky.swagger.coverage.configuration.options.ResultsWriterOptions;
import com.github.viclovsky.swagger.coverage.core.generator.Generator;
import org.apache.commons.io.FileUtils;
import org.hamcrest.io.FileMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import static java.nio.file.Paths.get;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.StringContainsInOrder.stringContainsInOrder;

@RunWith(Parameterized.class)
public class CustomReportTemplateTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomReportTemplateTest.class);
    private final String outputPath, specPath;

    public CustomReportTemplateTest(String outputPath, String specPath ) {
        this.outputPath = outputPath;
        this.specPath = specPath;
    }

    private File generateConfigurationFile() throws URISyntaxException, IOException {
        HashMap<String, ResultsWriterOptions> customReportOptions = new HashMap<>();
        URL res = getClass().getClassLoader().getResource("report_custom.ftl");
        customReportOptions.put("html", new ResultsWriterOptions()
                .setFilename("custom-template-report.html")
                .setLocale("en")
                .setNumberFormat("0.###")
                .setCustomTemplatePath(Paths.get(res.toURI()).toFile().getAbsolutePath()));
        ConfigurationOptions configurationOptions = new ConfigurationOptions().setWriters(customReportOptions);
        File testConfigurationFile = File.createTempFile("customTemplate", ".json");
        FileUtils.writeStringToFile(testConfigurationFile, new ObjectMapper().writeValueAsString(configurationOptions));
        return testConfigurationFile;
    }

    @Test
    public void testWithCustomTemplate() throws IOException, URISyntaxException {
        File testConfigurationFile = generateConfigurationFile();
        Config testConfig = new Config(testConfigurationFile.getAbsolutePath(),
                outputPath, specPath);
        LOGGER.info("Generate report for {}:", testConfigurationFile.getAbsolutePath());
        LOGGER.info("{}", FileUtils.readFileToString(testConfigurationFile));
        new Generator()
                .setInputPath(testConfig.getOutput())
                .setSpecPath(testConfig.getSpec())
                .setConfigurationPath(testConfigurationFile.toPath())
                .run();

        File reportFile = get("custom-template-report.html").toFile();
        assertThat(reportFile, FileMatchers.anExistingFile());
        assertThat(FileUtils.readFileToString(reportFile), stringContainsInOrder("CUSTOM_TEST_REPORT"));
    }

    @Parameterized.Parameters()
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][]{
                //Swagger v2
                {"v2/swagger-coverage-output", "v2/petstory.json"},
                //Swagger v3
                {"v3/swagger-coverage-output", "v3/petstory.yaml"},
        });
    }

}
