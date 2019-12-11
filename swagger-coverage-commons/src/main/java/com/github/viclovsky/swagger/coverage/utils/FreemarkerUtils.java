package com.github.viclovsky.swagger.coverage.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author eroshenkoam (Artem Eroshenko).
 */
public final class FreemarkerUtils {

    private final static String TEMPLATES = "templates";

    private FreemarkerUtils() {
    }

    public static String processTemplate(final String path, final Object object) {
        final Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);

        configuration.setClassForTemplateLoading(FreemarkerUtils.class, "/");
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
