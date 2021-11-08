package com.github.viclovsky.swagger.coverage.configuration.options;

public class ResultsWriterOptions {
    private String filename;
    private String locale = "en";
    private String customTemplatePath;
    private String numberFormat = "0.###";

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
                ", numberFormat='" + numberFormat + '\'' +
                '}';
    }

    public ResultsWriterOptions setCustomTemplatePath(String customTemplatePath) {
        this.customTemplatePath = customTemplatePath;
        return this;
    }

    public String getCustomTemplatePath() {
        return customTemplatePath;
    }

    public String getNumberFormat() {return numberFormat;}

    public ResultsWriterOptions setNumberFormat(String numberFormat){
        this.numberFormat = numberFormat;
        return this;
    }
}
