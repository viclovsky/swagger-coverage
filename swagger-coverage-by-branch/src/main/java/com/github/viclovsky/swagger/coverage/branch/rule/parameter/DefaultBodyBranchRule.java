package com.github.viclovsky.swagger.coverage.branch.rule.parameter;

import com.github.viclovsky.swagger.coverage.branch.model.Branch;
import com.github.viclovsky.swagger.coverage.branch.predicate.BranchPredicate;
import com.github.viclovsky.swagger.coverage.branch.predicate.DefaultBodyBranchPredicate;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.Parameter;

public class DefaultBodyBranchRule extends ParameterRule {

    @Override
    public Branch processParameter(Parameter parameter) {
        if (parameter instanceof BodyParameter) {
            Branch branch = new Branch(
                    "Not empty body request",
                    ""
            );

            BranchPredicate predicate = new DefaultBodyBranchPredicate();
            branch.addPredicate(predicate);

            return branch;
        }

        return null;
    }
}
