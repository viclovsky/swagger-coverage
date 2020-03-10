package com.github.viclovsky.swagger.coverage.branch.results;

public class GenerationStatistics {

    protected long resultFileCount = 0;
    protected long generationTime;

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
