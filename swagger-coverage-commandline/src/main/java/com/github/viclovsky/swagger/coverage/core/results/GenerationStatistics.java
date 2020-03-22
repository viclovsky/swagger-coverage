package com.github.viclovsky.swagger.coverage.core.results;

public class GenerationStatistics {

    private long resultFileCount = 0;
    private long generationTime;

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
}
