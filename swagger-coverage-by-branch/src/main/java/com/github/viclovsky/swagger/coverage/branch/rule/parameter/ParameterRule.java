package com.github.viclovsky.swagger.coverage.branch.rule.parameter;

import com.github.viclovsky.swagger.coverage.branch.model.Branch;
import com.github.viclovsky.swagger.coverage.branch.rule.core.BranchRule;
import io.swagger.models.Operation;
import io.swagger.models.parameters.Parameter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Base rule for parameter
 */
public abstract class ParameterRule extends BranchRule {

    public abstract Branch processParameter(Parameter parameter);

    public List<Branch> createBranch(Operation operation) {
        return operation.getParameters()
                .stream()
                .map(this::processParameter)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
