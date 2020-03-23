package com.github.viclovsky.swagger.coverage.branch.configuration.options;

public class ResultsWriterOptions {
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

    @Override
    public String toString() {
        return "ResultsWriterOptions{" +
                ", filename='" + filename + '\'' +
                ", locale='" + locale + '\'' +
                '}';
    }
}
