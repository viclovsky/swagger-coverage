package com.github.viclovsky.swagger.coverage.branch.writer;

import com.github.viclovsky.swagger.coverage.CoverageResultsWriter;
import com.github.viclovsky.swagger.coverage.branch.generator.Generator;
import com.github.viclovsky.swagger.coverage.utils.FreemarkerUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import static com.github.viclovsky.swagger.coverage.SwaggerCoverageConstants.COVERAGE_HTML_REPORT_NAME;

public class HtmlBranchReportResultsWriter implements CoverageResultsWriter {

    private static final Logger log = LoggerFactory.getLogger(HtmlBranchReportResultsWriter.class);

    public HtmlBranchReportResultsWriter() {
    }

    @Override
    public void write(Object results) {
        final String htmlResults = "swagger-coverage-by-branch-report.html";
        Path path = Paths.get(htmlResults);
        log.info(String.format("Write html report in file '%s'", path.toAbsolutePath()));
        try {
            final String htmlReport = processTemplate("branchreport.ftl", results);
            Files.write(Paths.get(htmlResults), htmlReport.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String processTemplate(final String path, final Object object) {
        final Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);

        configuration.setClassForTemplateLoading(Generator.class, "/");
        configuration.setDefaultEncoding("UTF-8");

        try {
            final Map<String, Object> data = new HashMap<>();
            data.put("data", object);

            final Writer writer = new StringWriter();
            final Template template = configuration.getTemplate(path);
            template.process(data, writer);
            return writer.toString();
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
    }
}
