package com.github.viclovsky.swagger.coverage.branch.rule.parameter;

import com.github.viclovsky.swagger.coverage.branch.model.Branch;
import com.github.viclovsky.swagger.coverage.branch.predicate.BranchPredicate;
import com.github.viclovsky.swagger.coverage.branch.predicate.DefaultBranchPredicate;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.Parameter;

public class DefaultParameterBranchRule extends ParameterRule {

    @Override
    public Branch processParameter(Parameter parameter) {
        if (parameter instanceof BodyParameter) {
            return null;
        }

        Branch branch = new Branch(
                String.format("Not empty %s «%s»",  parameter.getIn(), parameter.getName()),
                ""
        );

        BranchPredicate predicate = new DefaultBranchPredicate(false, parameter.getName(), parameter.getIn());
        branch.addPredicate(predicate);

        return branch;
    }

}
