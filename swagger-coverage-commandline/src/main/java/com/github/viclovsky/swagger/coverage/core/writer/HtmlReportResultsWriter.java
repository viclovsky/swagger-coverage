package com.github.viclovsky.swagger.coverage.core.writer;

import com.github.viclovsky.swagger.coverage.SwaggerCoverageWriteException;
import com.github.viclovsky.swagger.coverage.configuration.options.ResultsWriterOptions;
import com.github.viclovsky.swagger.coverage.core.results.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.github.viclovsky.swagger.coverage.utils.FreemarkerUtils.processCustomTemplate;
import static com.github.viclovsky.swagger.coverage.utils.FreemarkerUtils.processTemplate;

public class HtmlReportResultsWriter implements CoverageResultsWriter {

    private static final Logger LOGGER = LoggerFactory.getLogger(HtmlReportResultsWriter.class);

    private ResultsWriterOptions options;

    private String filename = "swagger-coverage-report.html";
    private String localeCode = "en";
    private String numberFormat = "0.###";

    public HtmlReportResultsWriter() {
        options = new ResultsWriterOptions()
                .setFilename(filename)
                .setLocale(localeCode)
                .setNumberFormat(numberFormat);
    }

    public HtmlReportResultsWriter(ResultsWriterOptions options){
        if (options.getLocale() == null){
            options.setLocale(localeCode);
        }

        if (options.getFilename() == null){
            options.setFilename(filename);
        }

        if (options.getNumberFormat() == null){
            options.setNumberFormat(numberFormat);
        }

        this.options = options;
    }

    @Override
    public void write(Results results) {
        Path path = Paths.get(options.getFilename());
        LOGGER.info(String.format("Write html report in file '%s'", path.toAbsolutePath()));
        try {
            final String htmlReport = (options.getCustomTemplatePath() == null ) ?
                    processTemplate("report.ftl", options.getLocale(), options.getNumberFormat(), results) :
                    processCustomTemplate(options.getCustomTemplatePath(), options.getLocale(), options.getNumberFormat(), results);
            Files.write(Paths.get(options.getFilename()), htmlReport.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new SwaggerCoverageWriteException("Could not write results", e);
        }
    }

}
