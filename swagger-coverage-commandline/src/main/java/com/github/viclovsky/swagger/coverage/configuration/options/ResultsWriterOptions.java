package com.github.viclovsky.swagger.coverage.configuration.options;

public class ResultsWriterOptions {
    private String filename;
    private String locale = "en";
    private String customTemplatePath;

    public String getFilename() {
        return filename;
    }

    public ResultsWriterOptions setFilename(String filename) {
        this.filename = filename;
        return this;
    }

    public String getLocale() {
        return locale;
    }

    public ResultsWriterOptions setLocale(String locale) {
        this.locale = locale;
        return this;
    }

    @Override
    public String toString() {
        return "ResultsWriterOptions{" +
                ", filename='" + filename + '\'' +
                ", locale='" + locale + '\'' +
                ", customTemplatePath='" + customTemplatePath + '\'' +
                '}';
    }

    public ResultsWriterOptions setCustomTemplatePath(String customTemplatePath) {
        this.customTemplatePath = customTemplatePath;
        return this;
    }

    public String getCustomTemplatePath() {
        return customTemplatePath;
    }
}
