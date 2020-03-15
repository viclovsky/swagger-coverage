package com.github.viclovsky.swagger.coverage.branch.rule.parameter;

import com.github.viclovsky.swagger.coverage.branch.model.Branch;
import com.github.viclovsky.swagger.coverage.branch.model.SinglePredicateBranch;
import com.github.viclovsky.swagger.coverage.branch.predicate.BranchPredicate;
import com.github.viclovsky.swagger.coverage.branch.predicate.SimpleBranchPredicate;
import com.github.viclovsky.swagger.coverage.branch.rule.parameter.ParameterRule;
import io.swagger.models.parameters.HeaderParameter;
import io.swagger.models.parameters.Parameter;

public class EmptyHeaderBranchRule extends ParameterRule {
    @Override
    public Branch processParameter(Parameter parameter) {
        if (parameter instanceof HeaderParameter) {

            BranchPredicate predicate = new SimpleBranchPredicate(true,parameter.getName());
            Branch branch = new SinglePredicateBranch(
                    "Empty header " + parameter.getName(),
                    "",
                    predicate
            );

            return branch;
        }

        return null;
    }

    @Override
    public String getId() {
        return null;
    }
}
