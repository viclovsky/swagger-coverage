package com.github.viclovsky.swagger.coverage.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author eroshenkoam (Artem Eroshenko).
 */
public final class FreemarkerUtils {

    private final static String TEMPLATES = "templates";

    private FreemarkerUtils() {
    }

    public static String processTemplate(final String path, final Object object) {
        return processTemplate(path,"en",object);
    }

    public static String processTemplate(final String path, String locale, final Object object) {
        final Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);

        configuration.setClassForTemplateLoading(FreemarkerUtils.class, "/");
        configuration.setDefaultEncoding("UTF-8");

        Map<String, String> messages = readMessages(locale);

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
