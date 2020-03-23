package com.github.viclovsky.swagger.coverage.branch.writer;

import com.github.viclovsky.swagger.coverage.CoverageResultsWriter;
import com.github.viclovsky.swagger.coverage.branch.configuration.options.ResultsWriterOptions;
import com.github.viclovsky.swagger.coverage.branch.generator.Generator;
import com.github.viclovsky.swagger.coverage.utils.FreemarkerUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import static com.github.viclovsky.swagger.coverage.SwaggerCoverageConstants.COVERAGE_HTML_REPORT_NAME;

public class HtmlBranchReportResultsWriter implements CoverageResultsWriter {

    private static final Logger log = LoggerFactory.getLogger(HtmlBranchReportResultsWriter.class);

    protected String filename = "swagger-coverage-by-branch-report.html";
    protected String localeCode = "en";

    public HtmlBranchReportResultsWriter() {
    }

    public HtmlBranchReportResultsWriter(ResultsWriterOptions options) {
        if (options.getLocale() != null) {
            localeCode = options.getLocale();
        }
        if (options.getFilename() != null) {
            filename = options.getFilename();
        }
    }

    @Override
    public void write(Object results) {
        Path path = Paths.get(filename);
        log.info(String.format("Write html report in file '%s'", path.toAbsolutePath()));
        try {
            final String htmlReport = processTemplate("branchreport.ftl", results);
            Files.write(Paths.get(filename), htmlReport.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  String processTemplate(final String path, final Object object) {
        final Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);

        configuration.setClassForTemplateLoading(Generator.class, "/");
        configuration.setDefaultEncoding("UTF-8");

        Map<String, String> messages = readMessages(localeCode);

        try {
            final Map<String, Object> data = new HashMap<>();
            data.put("data", object);
            data.put("messages", messages);

            final Writer writer = new StringWriter();
            final Template template = configuration.getTemplate(path);
            template.process(data, writer);
            return writer.toString();
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, String> readMessages(String localeCode){
        Properties properties = new Properties();
        HashMap<String, String> mymap= new HashMap<String, String>();

        String resourceName = "message." + localeCode; // could also be a constant
        System.out.println("read property from " + resourceName);
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try(InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
            InputStreamReader isr = new InputStreamReader(resourceStream, "UTF-8");
            properties.load(isr);
        } catch (Exception e) {
            System.out.println("oops");
        }

        for (String key : properties.stringPropertyNames()) {
            String value = properties.getProperty(key);
            mymap.put(key, value);
        }

        return mymap;
    }
}
