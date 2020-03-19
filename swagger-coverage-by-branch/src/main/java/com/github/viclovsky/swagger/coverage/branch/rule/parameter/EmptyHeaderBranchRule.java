package com.github.viclovsky.swagger.coverage.branch.rule.parameter;

import com.github.viclovsky.swagger.coverage.branch.model.Branch;
import com.github.viclovsky.swagger.coverage.branch.predicate.BranchPredicate;
import com.github.viclovsky.swagger.coverage.branch.predicate.DefaultBranchPredicate;
import io.swagger.models.parameters.HeaderParameter;
import io.swagger.models.parameters.Parameter;

public class EmptyHeaderBranchRule extends ParameterRule {

    @Override
    public Branch processParameter(Parameter parameter) {
        if (parameter instanceof HeaderParameter) {
            Branch branch = new Branch(
                    "Empty header " + parameter.getName(),
                    ""
            );

            BranchPredicate predicate = new DefaultBranchPredicate(true, parameter.getName(), parameter.getIn());
            branch.addPredicate(predicate);

            return branch;
        }

        return null;
    }
}
