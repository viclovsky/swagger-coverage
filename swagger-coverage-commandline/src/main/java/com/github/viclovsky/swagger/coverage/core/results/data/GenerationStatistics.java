package com.github.viclovsky.swagger.coverage.core.results.data;

public class GenerationStatistics {

    private long resultFileCount = 0;
    private long generationTime;
    private String fileResultDateInterval;
    private String generateDate;

    public GenerationStatistics() {
    }

    public long getResultFileCount() {
        return resultFileCount;
    }

    public GenerationStatistics setResultFileCount(long resultFileCount) {
        this.resultFileCount = resultFileCount;
        return this;
    }

    public long getGenerationTime() {
        return generationTime;
    }

    public GenerationStatistics setGenerationTime(long generationTime) {
        this.generationTime = generationTime;
        return this;
    }

    public String getFileResultDateInterval() {
        return fileResultDateInterval;
    }

    public GenerationStatistics setFileResultDateInterval(String fileResultDateInterval) {
        this.fileResultDateInterval = fileResultDateInterval;
        return this;
    }

    public String getGenerateDate() {
        return generateDate;
    }

    public GenerationStatistics setGenerateDate(String generateDate) {
        this.generateDate = generateDate;
        return this;
    }
}
