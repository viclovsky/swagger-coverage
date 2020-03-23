package com.github.viclovsky.swagger.coverage.core.writer;

import com.github.viclovsky.swagger.coverage.core.results.Results;

public interface CoverageResultsWriter {
    void write(Results results);
}
