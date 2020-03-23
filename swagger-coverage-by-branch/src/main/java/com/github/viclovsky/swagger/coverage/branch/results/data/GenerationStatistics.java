package com.github.viclovsky.swagger.coverage.branch.results.data;

public class GenerationStatistics {

    protected long resultFileCount = 0;
    protected long generationTime;
    protected String fileResultDateInterval;
    protected String generateDate;

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
