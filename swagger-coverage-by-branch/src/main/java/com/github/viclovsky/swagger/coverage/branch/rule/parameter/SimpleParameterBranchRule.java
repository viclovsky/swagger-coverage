package com.github.viclovsky.swagger.coverage.branch.rule.parameter;

import com.github.viclovsky.swagger.coverage.branch.model.Branch;
import com.github.viclovsky.swagger.coverage.branch.model.SinglePredicateBranch;
import com.github.viclovsky.swagger.coverage.branch.predicate.BranchPredicate;
import com.github.viclovsky.swagger.coverage.branch.predicate.SimpleBranchPredicate;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.HeaderParameter;
import io.swagger.models.parameters.Parameter;

public class SimpleParameterBranchRule extends ParameterRule {
    @Override
    public Branch processParameter(Parameter parameter) {
        if (parameter instanceof BodyParameter) {
            return null;
        }

        BranchPredicate predicate = new SimpleBranchPredicate(false,parameter.getName());
        Branch branch = new SinglePredicateBranch(
            "Not empty " +  parameter.getIn() + " " + parameter.getName(),
            "",
            predicate
        );

        return branch;
    }

    @Override
    public String getId() {
        return null;
    }
}
