package com.github.viclovsky.swagger.coverage.branch.rule.status;

import com.github.viclovsky.swagger.coverage.branch.model.Branch;
import com.github.viclovsky.swagger.coverage.branch.rule.core.BranchRule;
import io.swagger.models.Operation;
import io.swagger.models.parameters.Parameter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class StatusBranchRule extends BranchRule {

    public abstract Branch processStatus(String statusCode);

    public List<Branch> createBranch(Operation operation){
        return operation
                .getResponses()
                .entrySet()
                .stream()
                .map(entry -> processStatus(entry.getKey()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList())
                ;
    }

}
