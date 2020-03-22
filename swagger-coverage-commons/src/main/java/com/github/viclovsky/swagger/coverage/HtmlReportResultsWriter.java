package com.github.viclovsky.swagger.coverage;

import org.apache.log4j.Logger;
import com.github.viclovsky.swagger.coverage.utils.FreemarkerUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.github.viclovsky.swagger.coverage.SwaggerCoverageConstants.COVERAGE_HTML_REPORT_NAME;

public class HtmlReportResultsWriter implements CoverageResultsWriter {

    private final static Logger LOGGER = Logger.getLogger(FileSystemResultsWriter.class);

    public HtmlReportResultsWriter() {
    }

    @Override
    public void write(Object results) {
        final String htmlResults = COVERAGE_HTML_REPORT_NAME;
        Path path = Paths.get(htmlResults);
        LOGGER.info(String.format("Write html report in file '%s'", path.toAbsolutePath()));
        try {
            final String htmlReport = FreemarkerUtils.processTemplate("branchreport.ftl", results);
            Files.write(Paths.get(htmlResults), htmlReport.getBytes());
        } catch (IOException e) {
            throw new SwaggerCoverageWriteException("Could not write results", e);
        }
    }
}
