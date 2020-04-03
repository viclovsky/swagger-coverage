package com.github.viclovsky.swagger.coverage.core.results.data;

public class ViewData {

    protected String projectName;

    public ViewData(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectName() {
        return projectName;
    }

    public ViewData setProjectName(String projectName) {
        this.projectName = projectName;
        return this;
    }
}
