package com.github.viclovsky.swagger.coverage.configuration.options;

public class ResultsWriterOptions {
    protected String type;
    protected String filename;
    protected String locale = "en";

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

    public String getType() {
        return type;
    }

    public ResultsWriterOptions setType(String type) {
        this.type = type;
        return this;
    }

    @Override
    public String toString() {
        return "ResultsWriterOptions{" +
                "type='" + type + '\'' +
                ", filename='" + filename + '\'' +
                ", locale='" + locale + '\'' +
                '}';
    }
}
