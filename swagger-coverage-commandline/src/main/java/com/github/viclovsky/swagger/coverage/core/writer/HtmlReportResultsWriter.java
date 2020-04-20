package com.github.viclovsky.swagger.coverage.core.writer;


import com.github.viclovsky.swagger.coverage.SwaggerCoverageWriteException;
import com.github.viclovsky.swagger.coverage.core.results.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.github.viclovsky.swagger.coverage.utils.FreemarkerUtils.processTemplate;

public class HtmlReportResultsWriter implements CoverageResultsWriter {

    private static final Logger log = LoggerFactory.getLogger(HtmlReportResultsWriter.class);

    protected String filename = "swagger-coverage-report.html";
    protected String localeCode = "en";

    public HtmlReportResultsWriter() {
    }

    public HtmlReportResultsWriter(String localeCode, String filename) {
        if (localeCode != null) {
            this.localeCode = localeCode;
        }
        if (filename != null) {
            this.filename = filename;
        }
    }

    @Override
    public void write(Results results) {
        Path path = Paths.get(filename);
        log.info(String.format("Write html report in file '%s'", path.toAbsolutePath()));
        try {
            final String htmlReport = processTemplate("report.ftl", localeCode, results);
            Files.write(Paths.get(filename), htmlReport.getBytes());
        } catch (IOException e) {
            throw new SwaggerCoverageWriteException("Could not write results", e);
        }
    }

}
