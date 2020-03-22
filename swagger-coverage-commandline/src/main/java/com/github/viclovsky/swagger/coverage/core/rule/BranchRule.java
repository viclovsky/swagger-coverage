package com.github.viclovsky.swagger.coverage.core.rule;

import com.github.viclovsky.swagger.coverage.core.model.Branch;
import io.swagger.models.Operation;

import java.util.List;

public abstract class BranchRule {
    public abstract List<Branch> createBranch(Operation operation);
}
